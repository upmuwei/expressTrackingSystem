package com.expressTracking.dao;

import com.expressTracking.entity.PackageRoute;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PackageRouteDao {

    public int update(PackageRoute packageRoute);

    public int insert(PackageRoute packageRoute);

    public int insertList(List<PackageRoute> packageRouteList);

    public int delete(Integer sn);

    public int deleteByPackageid(String packageId);

    public List<PackageRoute> getByPackageId(String packageId);

    public List<PackageRoute> getByPackageIds(List<String> packageIdList);

}
