package com.expresstracking.dao;

import com.expresstracking.entity.TransNode;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransNodeDao {
    public int update(TransNode transNode);

    public int delete(String id);

    public void insert(TransNode transNode);

    public TransNode get(String id);

    public List<TransNode> getAll();

    public List<TransNode> findByRegionCodeAndType(String regionCode,int type);
}