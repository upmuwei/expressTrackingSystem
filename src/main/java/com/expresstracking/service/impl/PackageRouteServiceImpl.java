package com.expresstracking.service.impl;

import com.expresstracking.entity.PackageRoute;
import com.expresstracking.service.PackageRouteService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
public class PackageRouteServiceImpl implements PackageRouteService {
    @Override
    public void save(PackageRoute route) {

    }

    @Override
    public int update(PackageRoute route) {
        return 0;
    }

    @Override
    public List<PackageRoute> getPackageRouteList(String expressSheetId) {
        return null;
    }
}
