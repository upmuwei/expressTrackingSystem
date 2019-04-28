package com.expressTracking.service.impl;

import com.expressTracking.dao.PackageRecordDao;
import com.expressTracking.dao.TransPackageDao;
import com.expressTracking.entity.PackageRecord;
import com.expressTracking.entity.TransPackage;
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

    @Autowired
    public TransPackageImpl(TransPackageDao transPackageDao, PackageRecordDao packageRecordDao) {
        this.transPackageDao = transPackageDao;
        this.packageRecordDao = packageRecordDao;
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
}
