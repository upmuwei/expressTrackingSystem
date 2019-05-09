package com.expressTracking.service;

import com.expressTracking.entity.CustomerInfo;

import java.sql.SQLException;
import java.util.List;

public interface CustomerInfoService {

    public CustomerInfo get(int id);

    public List<CustomerInfo> findByName(String name);

    public List<CustomerInfo> findByTelCode(String telCode);

    public List<CustomerInfo> findByParameter(CustomerInfo customerInfo);

    public int save(CustomerInfo customerInfo);

    public int removeById(int id);

    public int update(CustomerInfo customerInfo);

}
