package com.expressTracking.service;

import com.expressTracking.entity.PackageRoute;
import com.expressTracking.exception.ServiceException;

import java.util.Date;
import java.util.List;

public interface PackageRouteService {

    public int save(Integer userId, float x, float y, Date time) throws ServiceException,Exception;

    public int save(List<PackageRoute> routes);

    public int save(PackageRoute route) throws ServiceException,Exception;

    public int remove(int routeId) throws ServiceException,Exception;

    public int remove(String packageId) throws ServiceException,Exception;

    public int update(PackageRoute route) throws ServiceException,Exception;

    public List<PackageRoute> getPackageRouteList(String packageId) throws ServiceException,Exception;

    public List<PackageRoute> getExpressSheetRouteList(String esId) throws ServiceException,Exception;
}
