package com.expressTracking.service.impl;

import com.expressTracking.dao.RegionDao;
import com.expressTracking.entity.Region;
import com.expressTracking.service.RegionService;
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
public class RegionServiceImpl implements RegionService {
    private final RegionDao regionDao;

    @Autowired
    public RegionServiceImpl(RegionDao regionDao) {
        this.regionDao = regionDao;
    }

    @Override
    public List<Region> getProvinceList() {
        return regionDao.getProvinceList();
    }

    @Override
    public List<Region> getCityList(String prv) {

        return regionDao.getCityList(prv);
    }

    @Override
    public List<Region> getTownList(String city) {

        return regionDao.getTownList(city);
    }

    @Override
    public String getRegionNameById(String code) {
        Region region=regionDao.get(code);
        return region.getPrv()+region.getCty()+region.getTwn();
    }

    @Override
    public Region getFullNameRegionById(String code) {
        return regionDao.get(code);
    }
}
