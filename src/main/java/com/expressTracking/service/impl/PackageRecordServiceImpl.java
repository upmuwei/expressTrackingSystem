package com.expressTracking.service.impl;


import com.expressTracking.dao.PackageRecordDao;
import com.expressTracking.entity.PackageRecord;
import com.expressTracking.service.PackageRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author muwei
 * @date 2019/4/25
 */
@Service
public class PackageRecordServiceImpl implements PackageRecordService {

    private final PackageRecordDao packageRecordDao;

    @Autowired
    public PackageRecordServiceImpl(PackageRecordDao packageRecordDao) {
        this.packageRecordDao = packageRecordDao;
    }

    @Override
    public List<PackageRecord> findByPackageId(String packageId) {
        return packageRecordDao.selectByPackageId(packageId);
    }

    @Override
    public List<PackageRecord> findByuId(int uId) {
        return packageRecordDao.selectByuId(uId);
    }

    @Override
    public int packOk(String transPackageId, int uId) {
        return addPackageRecord(transPackageId,uId,PackageRecord.PACKAGE_PACK);
    }

    @Override
    public int addPackageRecord(String packageId, int userId, int operation) {
        PackageRecord packageRecord = new PackageRecord();
        packageRecord.setPackageId(packageId);
        packageRecord.setuId(userId);
        packageRecord.setOperation(operation);
        return packageRecordDao.insert(packageRecord);
    }
}
