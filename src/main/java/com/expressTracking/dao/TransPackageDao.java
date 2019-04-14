package com.expressTracking.dao;

import com.expressTracking.entity.TransPackage;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransPackageDao {
    public int update(TransPackage transPackage);

    public void insert(TransPackage transPackage);

    public TransPackage get(String id);

    /**
     * 依据id排序，升序
     */
    public List<TransPackage> findBy(@Param("propertyName") String propertyName,@Param("value") String value);

    public List<TransPackage> findLike(@Param("propertyName") String propertyName,@Param("value") String value);
}
