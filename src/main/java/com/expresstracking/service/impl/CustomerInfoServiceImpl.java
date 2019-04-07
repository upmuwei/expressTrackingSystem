package com.expresstracking.service.impl;

import com.expresstracking.entity.CustomerInfo;
import com.expresstracking.service.CustomerInfoService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
public class CustomerInfoServiceImpl implements CustomerInfoService {

    @Override
    public CustomerInfo get(int id) {
        return null;
    }

    @Override
    public List<CustomerInfo> findByName(String name) {
        return null;
    }

    @Override
    public List<CustomerInfo> findByTelCode(String telCode) {
        return null;
    }

    @Override
    public void save(CustomerInfo customerInfo) {

    }

    @Override
    public int removeById(int id) {
        return 0;
    }

    @Override
    public int findByPropertyWithoutID(CustomerInfo customerInfo) {
        return 0;
    }
}
