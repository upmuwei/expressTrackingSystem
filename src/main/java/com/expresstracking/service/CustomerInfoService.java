package com.expresstracking.service;

import com.expresstracking.entity.CustomerInfo;

import java.util.List;

public interface CustomerInfoService {

    public CustomerInfo get(int id);

    public List<CustomerInfo> findByName(String name);

    public List<CustomerInfo> findByTelCode(String telCode);

    public void save(CustomerInfo customerInfo);

    public int removeById(int id);

    public int findByPropertyWithoutID(CustomerInfo customerInfo);
}
