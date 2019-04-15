package com.expressTracking.service;

import com.expressTracking.entity.TransPackageContent;

import java.util.List;

public interface TransPackageContentService {


    public void save(TransPackageContent transPackageContent);

    public int update(TransPackageContent transPackageContent);

    /**
     * 依据快件id和包裹内容状态查询包裹内容
     * @param expressId 快件id
     * @param status 包裹内容状态
     * @return 包裹内容
     */
    public TransPackageContent findByExpressIdAndStatus(String expressId, int status);

    public List<TransPackageContent> findByPackageIdAndStatus(String packageId, int status);



}
