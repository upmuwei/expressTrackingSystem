package com.expresstracking.dao;

import com.expresstracking.entity.PackageRoute;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PackageRouteDao {

    public int update(PackageRoute packageRoute);

    public int delete(int sn);

    public void insert(PackageRoute packageRoute);

    public PackageRoute getBySn(int sn);

    public PackageRoute getByPackageId(String packageId);

    //public List<PackageRoute> getAll();
}
