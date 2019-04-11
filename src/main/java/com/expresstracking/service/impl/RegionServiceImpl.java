package com.expresstracking.service.impl;

import com.expresstracking.dao.RegionDao;
import com.expresstracking.entity.Region;
import com.expresstracking.service.RegionService;
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
public class RegionServiceImpl implements RegionService {
    @Autowired
    private RegionDao regionDao;
    @Override
    public List<Region> getProvinceList() {

        return null;
    }

    @Override
    public List<Region> getCityList(String city) {

        return null;
    }

    @Override
    public List<Region> getTownList(String tow) {

        return null;
    }

    @Override
    public String getRegionNameById(String code) {
        Region region=regionDao.get(code);
        String name=region.getPrv()+region.getCty()+region.getTwn();
        return name;
    }

    @Override
    public String getFullNameRegionById(String code) {
        Region region=regionDao.get(code);
        return "Region[ " +
                "RegionCode=" + region.getRegionCode() + " " +
                "Prv=" + region.getPrv() + " " +
                "Cty=" + region.getCty() + " " +
                "Twn=" + region.getTwn() + " " +
                "Stage=" + region.getStage() + " " +
                "]";
    }
}
