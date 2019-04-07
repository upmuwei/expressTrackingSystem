package com.expresstracking.dao;

import com.expresstracking.entity.ExpressSheet;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExpressSheetDao {

    public int update(ExpressSheet customerInfo);

    public int delete(String id);

    public void insert(ExpressSheet customerInfo);

    public ExpressSheet get(String id);

    public List<ExpressSheet> getAll();
}
