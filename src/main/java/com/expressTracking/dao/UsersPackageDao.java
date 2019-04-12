package com.expressTracking.dao;

import com.expressTracking.entity.UsersPackage;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UsersPackageDao {
    public int update(UsersPackage userPackage);

    public int delete(int sn);

    public void insert(UsersPackage userPackage);

    public List<UsersPackage> get(int id);

    public List<UsersPackage> getAll();

    public String getPackageId(int userId);
}
