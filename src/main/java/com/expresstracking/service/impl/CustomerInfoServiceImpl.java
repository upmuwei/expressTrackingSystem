package com.expresstracking.service.impl;

import com.expresstracking.dao.CustomerInfoDao;
import com.expresstracking.entity.CustomerInfo;
import com.expresstracking.service.CustomerInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author 19231
 * @date 2019/4/8
 */
@Service
@Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
public class CustomerInfoServiceImpl implements CustomerInfoService {
    @Autowired
    private CustomerInfoDao customerInfoDao;
    @Override
    public CustomerInfo get(int id) {
        return customerInfoDao.get(id);
    }

    @Override
    public List<CustomerInfo> findByName(String name) {
        return customerInfoDao.findByName(name);
    }

    @Override
    public List<CustomerInfo> findByTelCode(String telCode) {
        return customerInfoDao.findByTelCode(telCode);
    }

    @Override
    public void save(CustomerInfo customerInfo) {
        customerInfoDao.insert(customerInfo);
    }

    @Override
    public int removeById(int id) {
        return customerInfoDao.delete(id);
    }

    @Override
    public int findByPropertyWithoutID(CustomerInfo customerInfo) {
        return 1;
    }
}
