package com.expressTracking.dao;

import com.expressTracking.entity.TransNode;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransNodeDao {

    public void insert(TransNode transNode);

    public TransNode get(String id);

    public List<TransNode> findByRegionCodeAndType(@Param("regionCode") String regionCode,@Param("type") int type);
}
