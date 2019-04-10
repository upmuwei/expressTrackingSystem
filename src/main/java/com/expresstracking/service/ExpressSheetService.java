package com.expresstracking.service;

import com.expresstracking.entity.ExpressSheet;

import java.util.List;

public interface ExpressSheetService {

    public int update(ExpressSheet expressSheet);

    public List<ExpressSheet> findBy(String propertyName, Object value, String orderBy, boolean isAsc);

    public List<ExpressSheet> findLike(String propertyName, Object value, String orderBy, boolean isAsc);

    public List<ExpressSheet> getListInPackage(String packageId);

    public ExpressSheet get(String id);

    public void save(ExpressSheet expressSheet);

    public int removeById(String id);

}
