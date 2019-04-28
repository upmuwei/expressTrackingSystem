package com.expressTracking.service;

import com.expressTracking.entity.TransPackage;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

public interface TransPackageService {

    public List<TransPackage> findBy(String propertyName, String value);

    public List<TransPackage> findLike(String propertyName, String value);

    public TransPackage get(String id);

    public void save(TransPackage transPackage);

    public void update(TransPackage transPackage);

    public int newTransPackage(TransPackage transPackage, int uId) throws Exception;

    public int openTransPackage(int uId, String packageId) throws Exception;
}
