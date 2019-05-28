package com.expressTracking.dao;

import com.expressTracking.entity.TransNode;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransNodeDao {

    public int insert(TransNode transNode);

    public List<TransNode> getByParameters(TransNode transNode);

    public TransNode getById(String id);

    public int update(TransNode transNode);
}
