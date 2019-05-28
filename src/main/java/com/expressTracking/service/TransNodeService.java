package com.expressTracking.service;

import com.expressTracking.entity.TransNode;

import java.util.List;

public interface TransNodeService {

    public List<TransNode> getByParameters(TransNode transNode);

    public int save(TransNode transNode);

    public int update(TransNode transNode);

    public TransNode get(String transNodeId);

}
