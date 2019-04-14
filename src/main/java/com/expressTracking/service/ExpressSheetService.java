package com.expressTracking.service;

import com.expressTracking.entity.ExpressSheet;

import java.util.List;

public interface ExpressSheetService {

    public int update(ExpressSheet expressSheet);

    public List<ExpressSheet> findBy(String propertyName, String value);

    public List<ExpressSheet> findLike(String propertyName, String value);

    public List<ExpressSheet> getListInPackage(String packageId);

    public ExpressSheet get(String id);

    public void save(ExpressSheet expressSheet);

    public int removeById(String id);

}
