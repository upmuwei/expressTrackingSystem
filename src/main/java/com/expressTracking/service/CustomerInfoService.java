package com.expressTracking.service;

import com.expressTracking.entity.CustomerInfo;

import java.util.List;

public interface CustomerInfoService {

    public CustomerInfo get(int id);

    public List<CustomerInfo> findByName(String name);

    public List<CustomerInfo> findByTelCode(String telCode);

    public void save(CustomerInfo customerInfo);

    public int removeById(int id);

    public int update(CustomerInfo customerInfo);

}
