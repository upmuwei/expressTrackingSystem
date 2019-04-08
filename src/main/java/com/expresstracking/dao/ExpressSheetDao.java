package com.expresstracking.dao;

import com.expresstracking.entity.ExpressSheet;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExpressSheetDao {

    public int update(ExpressSheet expressSheet);

    public int delete(String id);

    public void insert(ExpressSheet expressSheet);

    public ExpressSheet get(String id);

    public List<ExpressSheet> getAll();

    public List<ExpressSheet> findBy(String propertyName, Object value, String orderBy, boolean isAsc);

    public List<ExpressSheet> findLike(String propertyName, Object value, String orderBy, boolean isAsc);

    public List<ExpressSheet> getListInPackage(String packageId);
}
