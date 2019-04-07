package com.expresstracking.dao;

import com.expresstracking.entity.UsersPackage;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UsersPackageDao {
    public int update(UsersPackage userPackage);

    public int delete(int id);

    public void insert(UsersPackage userPackage);

    public UsersPackage get(int id);

    public List<UsersPackage> getAll();
}
