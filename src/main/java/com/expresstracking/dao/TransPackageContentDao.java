package com.expresstracking.dao;

import com.expresstracking.entity.TransPackageContent;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransPackageContentDao {
    public int update(TransPackageContent transPackageContent);

    public int delete(int id);

    public void insert(TransPackageContent transPackageContent);

    public List<String> selectExpressId(String transPackageId);

    public TransPackageContent get(int id);

    public List<TransPackageContent> getAll();

    public List<String> getPackageId(String expressId);
}
