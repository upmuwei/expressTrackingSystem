package com.expressTracking.service;

import com.expressTracking.entity.UsersPackage;
import java.util.List;

public interface UserPackageService {

    public List<UsersPackage> getTransPackageList(int userUId);

    UsersPackage findByPackageId(String packageId);

    public void save(UsersPackage userPackage);

    public int remove(int sn);
}
