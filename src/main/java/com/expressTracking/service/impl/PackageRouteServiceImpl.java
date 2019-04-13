package com.expressTracking.service.impl;

import com.expressTracking.dao.*;
import com.expressTracking.entity.PackageRoute;
import com.expressTracking.service.PackageRouteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 19231
 * @date 2019/4/8
 */
@Service
@Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
public class PackageRouteServiceImpl implements PackageRouteService {
    private final PackageRouteDao packageRouteDao;
    private final TransPackageContentDao transPackageContentDao;

    @Autowired
    public PackageRouteServiceImpl(PackageRouteDao packageRouteDao, TransPackageContentDao transPackageContentDao) {
        this.packageRouteDao = packageRouteDao;
        this.transPackageContentDao = transPackageContentDao;
    }

    @Override
    public void save(PackageRoute route) {
         packageRouteDao.insert(route);
    }

    @Override
    public int update(PackageRoute route) {
        return packageRouteDao.update(route);
    }


    @Override
    public List<PackageRoute> getPackageRouteList(String expressSheetId) {
        List<String> packageIds = transPackageContentDao.getPackageId(expressSheetId);
        List<PackageRoute> packageRouteList = new ArrayList<>();
        for(String packageId : packageIds) {
            List<PackageRoute> temp = packageRouteDao.getByPackageId(packageId);
            packageRouteList.addAll(temp);
        }
        return packageRouteList;
    }
}
