package com.expressTracking.service;

import com.expressTracking.entity.UsersPackage;

import javax.jws.soap.SOAPBinding;
import java.util.List;

public interface UserPackageService {

    public List<UsersPackage> getTransPackageList(int userUId);

    UsersPackage findByPackageId(String packageId);

    public int save(UsersPackage userPackage);

    public int save(String packageId, int userId);


    public int newUserPackage(String packageId, int userId);


    public int remove(int sn);

    /**
     * 确认包裹送达目的站点
     *
     * @param packageId 包裹Id
     * @param userId1   转运员Id
     * @param userId2   站点工作人员Id
     * @return 1 or 0
     * @throws Exception
     */
    public int arriveDestination(String packageId, int userId1, int userId2) throws Exception;

    /**
     * 为包裹指派转运人员
     *
     * @param packageId 包裹Id
     * @param nodeUId   节点工作人员Id
     * @param userId    转运员Id
     * @return 1 or 0
     */
    public int appointTransPorter(String packageId, int nodeUId, int userId) throws Exception;


    /*==============================================李伟===========================================================*/

    /**
     * 通过 包裹Id 和 userId 查询 用户包裹的联系表
     *
     * @param packageId
     * @param userId
     * @return
     * @throws Exception
     */
    public List<UsersPackage> getUserPackageList(String packageId, Integer userId) throws Exception;

    public UsersPackage getUserPackage(String packageId, Integer userId) throws Exception;

    public int remove(String packageId) throws Exception;


}
