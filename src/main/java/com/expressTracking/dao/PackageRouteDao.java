package com.expressTracking.dao;

import com.expressTracking.entity.PackageRoute;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PackageRouteDao {

    public int update(PackageRoute packageRoute);

    public void insert(PackageRoute packageRoute);

    public List<PackageRoute> getByPackageId(String packageId);

}
