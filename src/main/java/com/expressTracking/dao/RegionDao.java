package com.expressTracking.dao;

import com.expressTracking.entity.Region;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RegionDao {
    public Region get(String id);

    public List<Region> getProvinceList();

    public List<Region> getCityList(String prv);

    public List<Region> getTownList(String city);

}
