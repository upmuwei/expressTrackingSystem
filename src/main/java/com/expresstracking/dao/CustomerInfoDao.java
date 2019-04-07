package com.expresstracking.dao;

import com.expresstracking.entity.CustomerInfo;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface CustomerInfoDao {

    public int update(CustomerInfo customerInfo);

    public int delete(int id);

    public void insert(CustomerInfo customerInfo);

    public CustomerInfo get(int id);

    public List<CustomerInfo> getAll();

}
