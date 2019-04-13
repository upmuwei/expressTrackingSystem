package com.expressTracking.dao;

import com.expressTracking.entity.TransPackageContent;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.ListIterator;

@Repository
public interface TransPackageContentDao {
    public int update(TransPackageContent transPackageContent);

    public void insert(TransPackageContent transPackageContent);

    public List<String> selectExpressId(String packageId);

    public List<String> getPackageId(String expressId);

    public List<TransPackageContent> getByPackageId(String packageId);
}
