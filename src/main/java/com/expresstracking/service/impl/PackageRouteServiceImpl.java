package com.expresstracking.service.impl;

import com.expresstracking.dao.*;
import com.expresstracking.entity.PackageRoute;
import com.expresstracking.service.PackageRouteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author 19231
 * @date 2019/4/8
 */
@Service
@Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
public class PackageRouteServiceImpl implements PackageRouteService {
    @Autowired
    private PackageRouteDao packageRouteDao;
    @Override
    public void save(PackageRoute route) {
         packageRouteDao.insert(route);
    }

    @Override
    public int update(PackageRoute route) {
        return packageRouteDao.update(route);
    }

    @Override
    //先通过快件查到包裹id，然后得到路线
    public List<PackageRoute> getPackageRouteList(String expressSheetId) {
        return packageRouteDao.getAll();
    }
}
