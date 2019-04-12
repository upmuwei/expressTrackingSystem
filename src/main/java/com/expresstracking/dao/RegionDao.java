package com.expresstracking.dao;

import com.expresstracking.entity.Region;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RegionDao {
    public int update(Region region);

    public int delete(int id);

    public void insert(Region region);

    public Region get(String id);

    public List<Region> getAll();

    //service中粘贴出来的方法

    public List<Region> getProvinceList();

    public List<Region> getCityList(String prv);

    public List<Region> getTownList(String city);

    public String getRegionNameById(String code);

    public Region getFullNameRegionById(String code);
}
