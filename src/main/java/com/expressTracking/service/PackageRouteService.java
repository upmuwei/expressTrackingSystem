package com.expressTracking.service;

import com.expressTracking.entity.PackageRoute;

import java.util.Date;
import java.util.List;

public interface PackageRouteService {

    public int save(Integer userId, float x, float y, Date time) throws Exception;

    public int save(List<PackageRoute> routes);

    public int save(PackageRoute route) throws Exception;

    public int remove(int routeId) throws Exception;

    public int remove(String packageId) throws Exception;

    public int update(PackageRoute route) throws Exception;

    public List<PackageRoute> getPackageRouteList(String packageId) throws Exception;

    public List<PackageRoute> getExpressSheetRouteList(String esId) throws Exception;
}
