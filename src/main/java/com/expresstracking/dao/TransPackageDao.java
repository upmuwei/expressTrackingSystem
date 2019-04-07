package com.expresstracking.dao;

import com.expresstracking.entity.TransPackage;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransPackageDao {
    public int update(TransPackage transPackage);

    public int delete(int id);

    public void insert(TransPackage transPackage);

    public TransPackage get(int id);

    public List<TransPackage> getAll();
}
