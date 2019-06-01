package com.expressTracking.service.impl;

import com.expressTracking.dao.ExpressSheetDao;
import com.expressTracking.dao.TransPackageContentDao;
import com.expressTracking.dao.UserInfoDao;
import com.expressTracking.entity.ExpressSheet;
import com.expressTracking.entity.TransPackageContent;
import com.expressTracking.entity.UserInfo;
import com.expressTracking.exception.ServiceException;
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
        //如果快件处于新建状态则将快件设为揽收状态
        if (expressSheet.getStatus() == ExpressSheet.STATUS.STATUS_CREATED) {
            expressSheet.setStatus(ExpressSheet.STATUS.STATUS_RECEIVED);
        }
        return expressSheetDao.update(expressSheet);
    }

    @Override
    public int updateEsStatus(String esId, int statsu) {
        return expressSheetDao.updateEsStatus(esId, statsu);
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
        List<TransPackageContent> transPackageContents = transPackageContentDao.getByPackageId(packageId);
        List<ExpressSheet> expressSheets = new ArrayList<>();
        for (TransPackageContent transPackageContent : transPackageContents) {
            if (transPackageContent.getStatus() == TransPackageContent.STATUS.STATUS_ACTIVE) {
                expressSheets.add(expressSheetDao.get(transPackageContent.getExpressId()));
            }
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
        expressSheet.setAccepter(accepter);
        expressSheet.setStatus(status);
        return getByParameters(expressSheet);
    }

    @Override
    public List<ExpressSheet> getByDeliverAndStatus(String deliver, Integer status) {
        ExpressSheet expressSheet = new ExpressSheet();
        expressSheet.setDeliver(deliver);
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
     *
     * @param expressSheet
     * @param accpter
     * @return 1 快件信息已存在 2 用户不存在 3 成功 4 失败
     */
    @Override
    public int create(ExpressSheet expressSheet, Integer accpter) {
        if (expressSheetDao.get(expressSheet.getId()) != null) {
            //快件信息已存在
            return 1;
        }

        UserInfo userInfo = userInfoDao.get(accpter);
        if (userInfo == null) {
            //用户信息不存在
            return 2;
        }

        expressSheet.setAccepter(userInfo.getuId() + "");
        //将快件状态设置揽收状态
        expressSheet.setStatus(ExpressSheet.STATUS.STATUS_RECEIVED);
        return save(expressSheet) *
                transPackageContentService.moveEsToPackage(userInfo.getReceivePackageId(), expressSheet.getId()) > 0 ? 3 : 4;
    }

    @Override
    public int save(ExpressSheet expressSheet) {
        System.out.println("esService=" + expressSheet);
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
    public List<ExpressSheet> getEsListFromPackage(String pckageId) {
        return expressSheetDao.selectEsByPackageId(pckageId);
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
    public int dispatchExpressSheet(String expressId, int uId) {

        UserInfo userInfo = userInfoDao.get(uId);
        ExpressSheet nes = expressSheetDao.get(expressId);
        if (userInfo == null) {
            throw new ServiceException(1000,"用户" + uId + "不存在");
        }
        if (nes == null) {
            throw new ServiceException(2000, "快件" + expressId + "不存在");
        }
        if (nes.getStatus() != ExpressSheet.STATUS.STATUS_PARTATION) {
            throw new ServiceException(2001, "包裹不处于分拣状态");
        }
        nes.setDeliver(uId + "");
        nes.setDeliveTime(new Date());
        nes.setStatus(ExpressSheet.STATUS.STATUS_DISPATCHED);
        return expressSheetDao.update(nes);
    }

    @Override
    public int deliveryExpressSheet(String expressId, int uId) {
        UserInfo userInfo = userInfoDao.get(uId);
        ExpressSheet nes = expressSheetDao.get(expressId);
        if (userInfo == null) {
            throw new ServiceException(1000,"用户" + uId + "不存在");
        }
        if (nes == null) {
            throw new ServiceException(2000, "快件" + expressId + "不存在");
        }
        if (nes.getStatus() != ExpressSheet.STATUS.STATUS_DISPATCHED) {
            throw new ServiceException(2001, "快递未派送，不能交付");
        }
        nes.setStatus(ExpressSheet.STATUS.STATUS_DELIVERIED);

        return expressSheetDao.update(nes);
    }
}
