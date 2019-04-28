package com.expressTracking.service;

import com.expressTracking.entity.UsersPackage;
import java.util.List;

public interface UserPackageService {

    public List<UsersPackage> getTransPackageList(int userUId);

    UsersPackage findByPackageId(String packageId);

    public void save(UsersPackage userPackage);

    public int remove(int sn);

    public int arriveDestination(String packageId, int userId1, int userId2) throws Exception;
}
