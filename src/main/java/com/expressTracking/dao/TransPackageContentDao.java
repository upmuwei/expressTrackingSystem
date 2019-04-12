package com.expressTracking.dao;

import com.expressTracking.entity.TransPackageContent;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransPackageContentDao {
    public int update(TransPackageContent transPackageContent);

    public void insert(TransPackageContent transPackageContent);

    public List<String> selectExpressId(String transPackageId);

    public List<String> getPackageId(String expressId);
}
