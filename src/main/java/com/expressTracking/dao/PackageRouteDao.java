package com.expressTracking.dao;

import com.expressTracking.entity.PackageRoute;
import org.springframework.stereotype.Repository;

@Repository
public interface PackageRouteDao {

    public int update(PackageRoute packageRoute);

    public int delete(int sn);

    public void insert(PackageRoute packageRoute);

    public PackageRoute getBySn(int sn);

    public PackageRoute getByPackageId(String packageId);

    //public List<PackageRoute> getAll();
}
