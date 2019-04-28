package com.expressTracking.service.impl;

import com.expressTracking.dao.ExpressSheetDao;
import com.expressTracking.dao.PackageRecordDao;
import com.expressTracking.dao.TransPackageContentDao;
import com.expressTracking.dao.TransPackageDao;
import com.expressTracking.entity.ExpressSheet;
import com.expressTracking.entity.PackageRecord;
import com.expressTracking.entity.TransPackage;
import com.expressTracking.entity.TransPackageContent;
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

    @Autowired
    public TransPackageImpl(TransPackageDao transPackageDao, PackageRecordDao packageRecordDao,
                            TransPackageContentDao transPackageContentDao, ExpressSheetDao expressSheetDao) {
        this.transPackageDao = transPackageDao;
        this.packageRecordDao = packageRecordDao;
        this.transPackageContentDao = transPackageContentDao;
        this.expressSheetDao = expressSheetDao;
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
        packageRecordDao.insert(packageRecord);
        transPackageDao.insert(transPackage);
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
}
