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
    public List<Region> getProvinceList() {//查询省名称不为空的
        return regionDao.getAll();
    }

    @Override
    public List<Region> getCityList(String prv) {//通过省名称查到code，然后like code的前二位

        return regionDao.getCityList(prv);
    }

    @Override
    public List<Region> getTownList(String city) {//like前四位

        return regionDao.getTownList(city);
    }

    @Override
    public String getRegionNameById(String code) {//获得不了省份名称
        Region region=regionDao.get(code);
        String name=region.getPrv()+region.getCty()+region.getTwn();
        return name;
    }

    @Override
    public Region getFullNameRegionById(String code) {
        Region region=regionDao.get(code);
        return region;
    }
}
