package com.expresstracking.service.impl;

import com.expresstracking.entity.Region;
import com.expresstracking.service.RegionService;
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
    @Override
    public List<Region> getProvinceList() {
        return null;
    }

    @Override
    public List<Region> getCityList(String prv) {
        return null;
    }

    @Override
    public List<Region> getTownList(String city) {
        return null;
    }

    @Override
    public String getRegionNameById(String code) {
        return null;
    }

    @Override
    public Region getFullNameRegionById(String code) {
        return null;
    }
}
