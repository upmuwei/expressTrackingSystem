package com.expresstracking.service;

import com.expresstracking.entity.TransPackage;

import java.util.List;

public interface TransPackageService {

    public List<TransPackage> findBy(String propertyName, Object value, String orderBy, boolean isAsc);

    public List<TransPackage> findLike(String propertyName, Object value, String orderBy, boolean isAsc);

    public List<TransPackage> getListInPackage(String packageId);

    public TransPackage get(String id);

    public void save(TransPackage transPackage);

    public void update(TransPackage transPackage);
}
