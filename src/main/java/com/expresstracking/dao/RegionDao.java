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

}
