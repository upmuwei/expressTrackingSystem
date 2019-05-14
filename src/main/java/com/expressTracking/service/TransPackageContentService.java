package com.expressTracking.service;

import com.expressTracking.entity.TransPackageContent;

import java.util.List;

public interface TransPackageContentService {


    public void save(TransPackageContent transPackageContent);

    public int update(TransPackageContent transPackageContent);

    public int updateStatusByPackageId(String packageId,Integer status);

    /**
     * 将快件信息添加到包裹中
     * @param packageId
     * @param esId
     * @return
     */
    public int moveEsToPackage(String packageId, String esId);


    /**
     * 将快件从包裹中移出
     * @param packageId
     * @param esId
     * @return
     */
    public int moveEsOutPackage(String packageId,String esId);

    /**
     * 依据快件id和包裹内容状态查询包裹内容
     *
     * @param expressId 快件id
     * @param status    包裹内容状态
     * @return 包裹内容
     */
    public TransPackageContent findByExpressIdAndStatus(String expressId, int status);

    public List<TransPackageContent> findByPackageIdAndStatus(String packageId, int status);

    public List<TransPackageContent> findByExpressId(String expressId);

//    public List<TransPackageContent> findByPackageId(String packageId);


    /**
     * 通过 包裹编号和快件编号查找包裹内容
     *
     * @param packageId
     * @param esId
     * @return
     */
    public TransPackageContent containExpress(String packageId, String esId);


}
