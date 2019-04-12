package com.expressTracking.service;

import com.expressTracking.entity.UsersPackage;
import java.util.List;

public interface UserPackageService {

    public List<UsersPackage> getTransPackageList(int userUId);

    public List<UsersPackage> findBy(String propertyName, Object value, String orderBy, boolean isAsc);

    public void save(UsersPackage userPackage);

    public int remove(int sn);
}
