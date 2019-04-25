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
    public void save(PackageRecord packageRecord) {
        packageRecordDao.insert(packageRecord);
    }
}
