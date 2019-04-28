package com.expressTracking.service.impl;

import com.expressTracking.dao.*;
import com.expressTracking.entity.*;
import com.expressTracking.service.TransPackageService;
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
@Service
@Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
public class TransPackageImpl implements TransPackageService {
    private final TransPackageDao transPackageDao;

    private final PackageRecordDao packageRecordDao;

    private final TransPackageContentDao transPackageContentDao;

    private final ExpressSheetDao expressSheetDao;

    private final UsersPackageDao usersPackageDao;

    @Autowired
    public TransPackageImpl(TransPackageDao transPackageDao, PackageRecordDao packageRecordDao,
                            TransPackageContentDao transPackageContentDao, ExpressSheetDao expressSheetDao,
                            UsersPackageDao usersPackageDao) {
        this.transPackageDao = transPackageDao;
        this.packageRecordDao = packageRecordDao;
        this.transPackageContentDao = transPackageContentDao;
        this.expressSheetDao = expressSheetDao;
        this.usersPackageDao = usersPackageDao;
    }

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
    public void save(TransPackage transPackage) {
        transPackageDao.insert(transPackage);
    }
    @Override
    public void update(TransPackage transPackage) {
        transPackageDao.update(transPackage);
    }

    @Override
    public int newTransPackage(TransPackage transPackage, int uId) throws Exception {
        if (transPackageDao.get(transPackage.getId()) != null) {
            throw new Exception("此包裹已存在");
        }
        transPackage.setCreateTime(new Date());
        PackageRecord packageRecord = new PackageRecord();
        packageRecord.setuId(uId);
        packageRecord.setPackageId(transPackage.getId());
        packageRecord.setOperation(0);
        transPackageDao.insert(transPackage);
        packageRecordDao.insert(packageRecord);
        return 1;
    }

    @Override
    public int openTransPackage(int uId, String packageId) throws Exception {
        TransPackage transPackage = transPackageDao.get(packageId);
        if(transPackage.getStatus() == 0){
            throw new Exception("包裹处于新建状态，未装入快件");
        }
        List<TransPackageContent> transPackageContents = transPackage.getContent();
        if(transPackageContents.isEmpty()){
            throw new Exception("传入包裹id不存在");
        }
        transPackage.setStatus(0);
        if (transPackageDao.update(transPackage) == 0) {
            return 0;
        }
        PackageRecord packageRecord = new PackageRecord();
        packageRecord.setPackageId(transPackage.getId());
        packageRecord.setuId(uId);
        packageRecord.setOperation(3);
        packageRecordDao.insert(packageRecord);
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
        PackageRecord packageRecord = new PackageRecord();
        packageRecord.setuId(uId);
        packageRecord.setPackageId(transPackage.getId());
        packageRecord.setOperation(2);
        packageRecordDao.insert(packageRecord);
        List<TransPackageContent> transPackageContents = transPackage.getContent();
        for(TransPackageContent transPackageContent:transPackageContents) {
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
