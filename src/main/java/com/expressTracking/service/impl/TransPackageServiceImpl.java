package com.expressTracking.service.impl;

import com.expressTracking.controller.DomainController;
import com.expressTracking.dao.*;
import com.expressTracking.entity.*;
import com.expressTracking.service.PackageRecordService;
import com.expressTracking.service.TransPackageService;
import com.expressTracking.service.UserInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * @author muwei
 * @date 2019/4/8
 */
@Service("transPackageService")
@Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
public class TransPackageServiceImpl implements TransPackageService {

    private static final Logger LOGGER = LoggerFactory.getLogger(TransPackageServiceImpl.class);
    @Autowired
    private TransPackageDao transPackageDao;
    @Autowired
    private TransPackageContentDao transPackageContentDao;
    @Autowired
    private ExpressSheetDao expressSheetDao;
    @Autowired
    private UsersPackageDao usersPackageDao;
    @Autowired
    private UserInfoService userInfoService;
    @Autowired
    private PackageRecordService packageRecordService;

    @Override
    public List<TransPackage> findBy(String propertyName, String value) {
        return transPackageDao.findBy(propertyName, value);
    }

    @Override
    public List<TransPackage> findLike(String propertyName, String value) {
        return transPackageDao.findLike(propertyName, value);
    }

    @Override
    public TransPackage get(String id) {
        return transPackageDao.get(id);
    }

    @Override
    public List<TransPackage> getByUserId(Integer userId, Integer operation, Integer status) {
        return transPackageDao.getByUserId(userId, operation, status);
    }

    @Override
    public int save(TransPackage transPackage) {
        return transPackageDao.insert(transPackage);
    }

    @Override
    public int update(TransPackage transPackage) {
        return transPackageDao.update(transPackage);
    }

    @Override
    public int newTransPackage(TransPackage transPackage, int uId) {
        transPackage.setStatus(TransPackage.PACKAGE_NEW);
        transPackage.setCreateTime(new Date());
        return transPackageDao.insert(transPackage) * packageRecordService.addPackageRecord(transPackage.getId(), uId, PackageRecord.PACKAGE_NEW);
    }

    @Override
    public int newTransPackage(String packageId, int userId) {
        TransPackage transPackage = new TransPackage();
        transPackage.setId(packageId);
        transPackage.setStatus(TransPackage.PACKAGE_NEW);
        transPackage.setCreateTime(new Date());
        return transPackageDao.insert(transPackage) * packageRecordService.addPackageRecord(transPackage.getId(), userId, PackageRecord.PACKAGE_NEW);
    }


    /**
     * 拆包
     * 步骤
     * 1 检查包裹是否存在 存在执行 2 不存在 返回 1
     * 2 检查用户是否存在 是 执行 3 否 返回 2
     * 3 将包裹中的TransPackageContent 置为 STATUS_OUTOF_PACKAGE
     * 4 将包裹中的所有快件状态置为 分拣状态STATUS_PARTATION
     * 5 添加包裹操作记录
     * 6 修改包裹状态
     * 7 返回 3 拆包成功
     *
     * @param packageId
     * @param userId
     * @return 1 包裹不存在 2 用户不存在 3 拆包成功
     */
    @Override
    public int unPackTransPckage(String packageId, int userId) {
        TransPackage transPackage = get(packageId);
        if (transPackage == null) {
            //包裹不存在
            return 1;
        }
        UserInfo userInfo = userInfoService.get(userId);
        if (userInfo == null) {
            //用户不存在
            return 2;
        }

        //将所有快件移出包裹中
        transPackageContentDao.updateStatusByPackageId(packageId, TransPackageContent.STATUS.STATUS_OUTOF_PACKAGE);
        for (TransPackageContent transPackageContent : transPackage.getContent()) {
            //将快件状态置为 分拣状态
            expressSheetDao.updateEsStatus(transPackageContent.getExpressId(), ExpressSheet.STATUS.STATUS_PARTATION);
        }

        //添加包裹操作历史
        packageRecordService.addPackageRecord(packageId, userId, PackageRecord.PACKAGE_UNPACK);
        //修改包裹状态
        transPackage.setStatus(TransPackage.PACKAGE_COMPLETE);
        update(transPackage);
        //成功
        return 3;

//        TransPackage transPackage = get(packageId);
//        //不能修改分拣货篮，揽收货篮，派送货篮的状态
////        if(transPackage.getStatus() = )
//        return transPackageDao.updatePackageStatus(packageId, TransPackage.PACKAGE_COMPLETE)
//                * packageRecordService.addPackageRecord(packageId, userId, PackageRecord.PACKAGE_UNPACK);
    }

    @Override
    public int openTransPackage(int uId, String packageId) throws Exception {
        UserInfo userInfo = userInfoService.get(uId);
        if (userInfo == null) {
            throw new Exception("用户信息不存在");
        }
        TransPackage transPackage = transPackageDao.get(packageId);
        if (transPackage.getStatus() == TransPackage.PACKAGE_NEW) {
            throw new Exception("包裹处于新建状态，未装入快件");
        } else if (transPackage.getStatus() == TransPackage.PACKAGE_COMPLETE) {
            throw new Exception("包裹已拆包");
        }

        List<TransPackageContent> transPackageContents = transPackage.getContent();
        if (transPackageContents.isEmpty()) {
            throw new Exception("包裹信息不存在");
        }

        transPackage.setStatus(0);
        if (transPackageDao.update(transPackage) == 0) {
            return 0;
        }
        PackageRecord packageRecord = new PackageRecord();
        packageRecord.setPackageId(transPackage.getId());
        packageRecord.setuId(uId);
        packageRecord.setOperation(PackageRecord.PACKAGE_UNPACK);
        packageRecordService.addPackageRecord(transPackage.getId(), uId, PackageRecord.PACKAGE_UNPACK);
        for (TransPackageContent transPackageContent : transPackageContents) {
            if (transPackageContent.getStatus() == 1) {
                continue;
            }
            transPackageContent.setStatus(TransPackageContent.STATUS.STATUS_OUTOF_PACKAGE);
            transPackageContentDao.update(transPackageContent);
            ExpressSheet expressSheet = expressSheetDao.get(transPackageContent.getExpressId());
            expressSheet.setStatus(ExpressSheet.STATUS.STATUS_PARTATION);
            expressSheetDao.update(expressSheet);
        }
        return 1;
    }

    @Override
    public int deliveryTransPackage(String packageId, int uId) throws Exception {
        UsersPackage usersPackage = usersPackageDao.getByPackageId(packageId);
        if (usersPackage.getUserUid() != uId) {
            throw new Exception("转运人员错误，不能转运");
        }
        TransPackage transPackage = transPackageDao.get(packageId);
        if (transPackage.getStatus() != 1) {
            throw new Exception("包裹状态信息错误,不能转运");
        }
        transPackage.setStatus(2);
        if (transPackageDao.update(transPackage) == 0) {
            return 0;
        }

        packageRecordService.addPackageRecord(transPackage.getId(), uId, PackageRecord.PACKAGE_TRANS);
        List<TransPackageContent> transPackageContents = transPackage.getContent();
        for (TransPackageContent transPackageContent : transPackageContents) {
            ExpressSheet expressSheet = expressSheetDao.get(transPackageContent.getExpressId());
            expressSheet.setStatus(ExpressSheet.STATUS.STATUS_TRANSPORT);
            expressSheetDao.update(expressSheet);
        }
        return 1;
    }

    @Override
    public int packTransPackage(String packageId, String expressId) throws Exception {
        TransPackage transPackage = transPackageDao.get(packageId);
        if (transPackage.getStatus() == 1 || transPackage.getStatus() == 2) {
            throw new Exception("包裹状态信息错误");
        }
        if (transPackage.getStatus() == 0) {
            transPackage.setStatus(1);
        }
        if (transPackageDao.update(transPackage) == 0) {
            return 0;
        }
        TransPackageContent transPackageContent = new TransPackageContent();
        transPackageContent.setPackageId(packageId);
        transPackageContent.setExpressId(expressId);
        transPackageContent.setStatus(TransPackageContent.STATUS.STATUS_ACTIVE);
        transPackageContentDao.insert(transPackageContent);
        return 1;
    }
}
