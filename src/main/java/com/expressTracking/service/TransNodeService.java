package com.expressTracking.service;

import com.expressTracking.entity.TransNode;

import java.util.List;

public interface TransNodeService {

    public TransNode get(String id);

    public List<TransNode> findByRegionCodeAndType(String regionCode,int type);

    public void save(TransNode transNode);
}
