package com.expressTracking.service.impl;

import com.expressTracking.dao.ExpressSheetDao;
import com.expressTracking.dao.TransPackageContentDao;
import com.expressTracking.dao.UserInfoDao;
import com.expressTracking.entity.ExpressSheet;
import com.expressTracking.entity.TransPackageContent;
import com.expressTracking.entity.UserInfo;
import com.expressTracking.service.ExpressSheetService;
import com.expressTracking.service.TransPackageContentService;
import com.expressTracking.utils.DateUtil;
import com.google.gson.annotations.Expose;
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
    @Autowired
    private ExpressSheetDao expressSheetDao;
    @Autowired
    private TransPackageContentDao transPackageContentDao;
    @Autowired
    private UserInfoDao userInfoDao;
    private TransPackageServiceImpl transPackage;
    @Autowired
    private TransPackageContentService transPackageContentService;


    @Override
    public int delete(String esId) {
        return expressSheetDao.delete(esId);
    }

    @Override
    public int update(ExpressSheet expressSheet) {
        return expressSheetDao.update(expressSheet);
    }

    @Override
    public List<ExpressSheet> findBy(String propertyName, String value) {
        return expressSheetDao.findBy(propertyName, value);
    }

    @Override
    public List<ExpressSheet> findLike(String propertyName, String value) {
        return expressSheetDao.findLike(propertyName, value);
    }

    @Override
    public List<ExpressSheet> getListInPackage(String packageId) {
        List<String> expressId = transPackageContentDao.getExpressId(packageId);
        List<ExpressSheet> expressSheets = new ArrayList<>();
        for (String id : expressId) {
            expressSheets.add(expressSheetDao.get(id));
        }
        return expressSheets;
    }

    @Override
    public ExpressSheet get(String id) {
        return expressSheetDao.get(id);
    }
//    @Override
//    public ExpressSheet getByExpressId(String id) {
//        return expressSheetDao.get(id);
//    }
//
//
//    @Override
//    public List<ExpressSheet> getByMoreConditions(ExpressSheet expressSheet) {
//        return expressSheetDao.findByMoreConditions(expressSheet);
//    }

    @Override
    public List<ExpressSheet> getByParameters(ExpressSheet expressSheet) {
        if (expressSheet != null) {
            return expressSheetDao.getByParameters(expressSheet);
        } else {
            return null;
        }
    }

    @Override
    public List<ExpressSheet> getByAccpterAndStatus(String accepter, Integer status) {
        ExpressSheet expressSheet = new ExpressSheet();
        expressSheet.setAccepter(accepter) ;
        expressSheet.setStatus(status);
        return getByParameters(expressSheet);
    }

    @Override
    public int create(String expressId, Integer accepter) {
        ExpressSheet expressSheet = new ExpressSheet();
        expressSheet.setId(expressId);
        expressSheet.setAccepter(accepter + "");
        expressSheet.setType(0);
        expressSheet.setStatus(ExpressSheet.STATUS.STATUS_CREATED);
        return expressSheetDao.insert(expressSheet);
    }

    /**
     * 创建快件信息并将快件添加到揽件员的揽件货篮中
     * 成功返回大于0
     * 失败返回小于0
     *
     * @param expressSheet
     * @param accpter
     * @return
     */
    @Override
    public int create(ExpressSheet expressSheet, Integer accpter) {
        UserInfo userInfo = userInfoDao.get(accpter);
        System.out.println(expressSheet + "\n" + userInfo);
        expressSheet.setAccepter(accpter + "");
        //expressSheet 或 快件编号 或 用户信息为空时返回0 表示创建快件信息失败
        if (expressSheet == null || expressSheet.getId() == null || userInfo == null) {
            return 0;
        } else {
            return save(expressSheet) *
                    transPackageContentService.moveEsToPackage(userInfo.getReceivePackageId(), expressSheet.getId());
        }
    }

    @Override
    public int save(ExpressSheet expressSheet) {
        if (expressSheet == null || expressSheetDao.get(expressSheet.getId()) != null) {
            return 0;
        } else {
            expressSheet.setType(0);
            expressSheet.setStatus(ExpressSheet.STATUS.STATUS_CREATED);
            expressSheet.setAccepteTime(DateUtil.getCurrentDate());
            return expressSheetDao.insert(expressSheet);
        }
    }


    @Override
    public int receiveExpressSheet(String expressId, int uId) throws Exception {
        ExpressSheet nes = expressSheetDao.get(expressId);
        if (nes.getStatus() != ExpressSheet.STATUS.STATUS_CREATED) {
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
        if (expressSheetDao.update(nes) == 0) {
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
