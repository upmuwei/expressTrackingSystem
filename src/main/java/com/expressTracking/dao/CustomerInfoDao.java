package com.expressTracking.dao;

import com.expressTracking.entity.CustomerInfo;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.util.List;

@Repository
public interface CustomerInfoDao {

    public int update(CustomerInfo customerInfo);

    public int delete(int id);

    public int insert(CustomerInfo customerInfo);

    public CustomerInfo get(int id);

    public List<CustomerInfo> findByName(String name);

    public List<CustomerInfo> findByTelCode(String telCode);

    public List<CustomerInfo> fingByParameter(CustomerInfo customerInfo);

}
