package com.expressTracking.service;

import com.expressTracking.entity.TransPackage;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 *
 */
public interface TransPackageService {

    public List<TransPackage> findBy(String propertyName, String value);

    public List<TransPackage> findLike(String propertyName, String value);

    public TransPackage get(String id);

    public List<TransPackage> getByUserId(Integer userId,Integer operation, Integer status);

    public int save(TransPackage transPackage);

    public int update(TransPackage transPackage);

    /**
     * 新建包裹
     * @param transPackage 包裹
     * @param uId 工作人员Id
     * @return
     * @throws Exception
     */
    public int newTransPackage(TransPackage transPackage, int uId);

    /**
     * 新建包裹
     * @param packageId 包裹编号
     * @param userId 创建的用户编号
     * @return
     */
    public int newTransPackage(String packageId,int userId);

    /**
     * 拆包
     * @param packageId
     * @param userId
     * @return
     */
    public int unPackTransPckage(String packageId, int userId);


    /**
     * 打开包裹
     * @param uId 工作人员Id
     * @param packageId 包裹Id
     * @return
     * @throws Exception
     */
    public int openTransPackage(int uId, String packageId) throws Exception;

    /**
     * 转运包裹
     * @param packageId 包裹Id
     * @param uId 工作人员Id
     * @return
     * @throws Exception
     */
    public int deliveryTransPackage(String packageId, int uId) throws Exception;

    /**
     * 打包
     * @param packageId 包裹Id
     * @param expressId 快件Id
     * @return
     */
    public int packTransPackage(String packageId, String expressId) throws Exception;



}
