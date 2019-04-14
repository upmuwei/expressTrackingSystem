package com.expressTracking.service;

import com.expressTracking.entity.TransPackage;

import java.util.List;

public interface TransPackageService {

    public List<TransPackage> findBy(String propertyName, String value);

    public List<TransPackage> findLike(String propertyName, String value);

    public TransPackage get(String id);

    public void save(TransPackage transPackage);

    public void update(TransPackage transPackage);
}
