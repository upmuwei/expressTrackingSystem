package com.expresstracking.service;

import com.expresstracking.entity.Region;

import java.util.List;

public interface RegionService {
    public List<Region> getProvinceList();

    public List<Region> getCityList(String prv);

    public List<Region> getTownList(String city);

    public String getRegionNameById(String code);

    public String getFullNameRegionById(String code);
}
