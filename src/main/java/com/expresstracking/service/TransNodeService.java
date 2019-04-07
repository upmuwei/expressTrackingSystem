package com.expresstracking.service;

import com.expresstracking.entity.TransNode;

import java.util.List;

public interface TransNodeService {

    public TransNode get(String id);

    public List<TransNode> findByRegionCodeAndType(String regionCode,int type);
}
