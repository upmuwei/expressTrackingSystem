package com.expressTracking.service;

import com.expressTracking.entity.UsersPackage;
import java.util.List;

public interface UserPackageService {

    public List<UsersPackage> getTransPackageList(int userUId);

    UsersPackage findByPackageId(String packageId);

    public void save(UsersPackage userPackage);



    public int newUserPackage(String packageId,int userId);


    public int remove(int sn);

    /**
     * 确认包裹送达目的站点
     * @param packageId 包裹Id
     * @param userId1 转运员Id
     * @param userId2 站点工作人员Id
     * @return 1 or 0
     * @throws Exception
     */
    public int arriveDestination(String packageId, int userId1, int userId2) throws Exception;

    /**
     * 为包裹指派转运人员
     * @param packageId 包裹Id
     * @param nodeUId 节点工作人员Id
     * @param userId 转运员Id
     * @return 1 or 0
     */
    public int appointTransPorter(String packageId, int nodeUId, int userId) throws Exception;
}
