package com.expressTracking.service;

import com.expressTracking.entity.PackageRecord;

import java.util.List;

/**
 * @author muwei
 * @date 2019/4/25
 */
public interface PackageRecordService {

    public List<PackageRecord> findByPackageId(String packageId);

    public List<PackageRecord> findByuId(int uId);

    public void packOk(String packageId, int uId);
}
