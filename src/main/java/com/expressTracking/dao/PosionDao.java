package com.expressTracking.dao;

import com.expressTracking.entity.Posion;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PosionDao {
    public int update(Posion posion);

    public int delete(int id);

    public void insert(Posion posion);

    public Posion get(int id);

    public List<Posion> getAll();
}
