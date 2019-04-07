package com.expresstracking.dao;

import com.expresstracking.entity.TransNode;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransNodeDao {
    public int update(TransNode transNode);

    public int delete(int id);

    public void insert(TransNode transNode);

    public TransNode get(int id);

    public List<TransNode> getAll();
}
