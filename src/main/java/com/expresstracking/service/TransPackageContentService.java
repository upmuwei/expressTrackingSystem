package com.expresstracking.service;

import com.expresstracking.entity.TransPackageContent;

import java.util.List;

public interface TransPackageContentService {

    //public int getSn(String id, String uId);

    public void save(TransPackageContent transPackageContent);

    public int update(TransPackageContent transPackageContent);

    public TransPackageContent get(String id, String sourcePkgId);

    public List<TransPackageContent> findBy(String orderBy, boolean isAsc, String expressSheetId, int status);

    public List<TransPackageContent> findBy(String propertyName, Object value, String orderBy, boolean isAsc);
}
