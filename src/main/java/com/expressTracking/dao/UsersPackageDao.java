package com.expressTracking.dao;

import com.expressTracking.entity.UsersPackage;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UsersPackageDao {

    public int delete(int sn);

    public void insert(UsersPackage userPackage);

    public List<UsersPackage> getByUserUId(int userUId);

}
