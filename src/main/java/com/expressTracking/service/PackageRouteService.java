package com.expressTracking.service;

import com.expressTracking.entity.PackageRoute;

import java.util.List;

public interface PackageRouteService {

    public void save(PackageRoute route);

    public int update(PackageRoute route);

    public List<PackageRoute> getPackageRouteList(String expressSheetId);

}
