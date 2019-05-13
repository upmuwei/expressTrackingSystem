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

    /**
     * 打包操作
     * @param packageId
     * @param uId
     * @return
     */
    public int packOk(String packageId, int uId);

    /**
     * 添加包裹操作记录
     * @param packageId
     * @param userId
     * @param operation
     * @return
     */
    public int addPackageRecord(String packageId,int userId,int operation);

}
