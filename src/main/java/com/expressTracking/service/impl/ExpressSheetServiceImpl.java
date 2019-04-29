package com.expressTracking.service.impl;

import com.expressTracking.dao.ExpressSheetDao;
import com.expressTracking.dao.TransPackageContentDao;
import com.expressTracking.dao.UserInfoDao;
import com.expressTracking.entity.ExpressSheet;
import com.expressTracking.entity.TransPackageContent;
import com.expressTracking.entity.UserInfo;
import com.expressTracking.service.ExpressSheetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author muwei
 * @date 2019/4/8
 */
@Service
@Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRES_NEW,
        rollbackFor = Exception.class)
public class ExpressSheetServiceImpl implements ExpressSheetService {
    private final ExpressSheetDao expressSheetDao;
    private final TransPackageContentDao transPackageContentDao;
    private final UserInfoDao userInfoDao;

    @Autowired
    public ExpressSheetServiceImpl(ExpressSheetDao expressSheetDao,
                                   TransPackageContentDao transPackageContentDao,
                                   UserInfoDao userInfoDao) {
        this.expressSheetDao = expressSheetDao;
        this.transPackageContentDao = transPackageContentDao;
        this.userInfoDao = userInfoDao;
    }

    @Override
    public int update(ExpressSheet expressSheet) {
        return expressSheetDao.update(expressSheet);
    }

    @Override
    public List<ExpressSheet> findBy(String propertyName, String value) {
        return expressSheetDao.findBy(propertyName,value);
    }

    @Override
    public List<ExpressSheet> findLike(String propertyName, String value) {
        return expressSheetDao.findLike(propertyName,value);
    }
    @Override
    public List<ExpressSheet> getListInPackage(String packageId) {
        List<String> expressId=transPackageContentDao.getExpressId(packageId);
        List<ExpressSheet> expressSheets = new ArrayList<>();
        for(String id:expressId){
            expressSheets.add(expressSheetDao.get(id));
        }
        return expressSheets;
    }

    @Override
    public ExpressSheet get(String id) {
        return expressSheetDao.get(id);
    }

    @Override
    public int save(ExpressSheet expressSheet) throws Exception{
        if (expressSheetDao.get(expressSheet.getId()) != null) {
            throw new Exception("此订单已存在，不能创建");
        }
        expressSheet.setType(0);
        expressSheet.setStatus(ExpressSheet.STATUS.STATUS_CREATED);
        int a = expressSheetDao.insert(expressSheet);
        System.out.println("\n\n\n" + a +"\n\n\n");
        return 1;

    }


    @Override
    public int receiveExpressSheet(String expressId, int uId) throws Exception {
        ExpressSheet nes = expressSheetDao.get(expressId);
        if(nes.getStatus() != ExpressSheet.STATUS.STATUS_CREATED){
            throw new Exception("快件运单状态错误!无法收件!");
        }
        nes.setAccepter(String.valueOf(uId));
        nes.setAccepteTime(new Date());
        nes.setStatus(ExpressSheet.STATUS.STATUS_RECEIVED);
        if (expressSheetDao.update(nes) == 0) {
            return 0;
        }
        UserInfo userInfo = userInfoDao.get(uId);
        TransPackageContent transPackageContent = new TransPackageContent();
        transPackageContent.setPackageId(userInfo.getReceivePackageId());
        transPackageContent.setExpressId(nes.getId());
        transPackageContent.setStatus(TransPackageContent.STATUS.STATUS_ACTIVE);
        transPackageContentDao.insert(transPackageContent);
        return 1;
    }

    @Override
    public int dispatchExpressSheet(String expressId, int uId) throws Exception {
        TransPackageContent transPackageContent = new TransPackageContent();
        ExpressSheet nes = expressSheetDao.get(expressId);
        if (nes.getStatus() != ExpressSheet.STATUS.STATUS_TRANSPORT) {
            throw new Exception("快递状态信息错误");
        }
        nes.setDeliver(String.valueOf(uId));
        nes.setDeliveTime(new Date());
        nes.setStatus(ExpressSheet.STATUS.STATUS_DISPATCHED);
        if (expressSheetDao.update(nes) == 0 ) {
            return 0;
        }
        transPackageContent.setPackageId(userInfoDao.get(uId).getDelivePackageId());
        transPackageContent.setExpressId(nes.getId());
        transPackageContent.setStatus(TransPackageContent.STATUS.STATUS_ACTIVE);
        transPackageContentDao.update(transPackageContent);
        return 1;
    }

    @Override
    public int deliveryExpressSheet(String expressId, int uId) throws Exception {
        ExpressSheet nes = expressSheetDao.get(expressId);
        if (nes.getStatus() != ExpressSheet.STATUS.STATUS_DISPATCHED) {
            throw new Exception("快递未派送，不能交付");
        }
        nes.setStatus(ExpressSheet.STATUS.STATUS_DELIVERIED);
        if (expressSheetDao.update(nes) == 0) {
            return 0;
        }
        TransPackageContent transPackageContent = transPackageContentDao.findByExpressIdAndStatus(expressId,
                TransPackageContent.STATUS.STATUS_ACTIVE);
        transPackageContent.setStatus(TransPackageContent.STATUS.STATUS_OUTOF_PACKAGE);
        transPackageContentDao.update(transPackageContent);
        return 1;
    }
}
