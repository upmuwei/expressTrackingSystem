package com.expressTracking.service.impl;

import com.expressTracking.dao.CustomerInfoDao;
import com.expressTracking.dao.RegionDao;
import com.expressTracking.entity.CustomerInfo;
import com.expressTracking.entity.Region;
import com.expressTracking.service.CustomerInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author muwei
 * @date 2019/4/8
 */
@Service
@Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
public class CustomerInfoServiceImpl implements CustomerInfoService {
    private final CustomerInfoDao customerInfoDao;

    private final RegionDao regionDao;

    @Autowired
    public CustomerInfoServiceImpl(CustomerInfoDao customerInfoDao, RegionDao regionDao) {
        this.customerInfoDao = customerInfoDao;
        this.regionDao = regionDao;
    }

    @Override
    public CustomerInfo get(int id) {
        CustomerInfo customerInfo = customerInfoDao.get(id);
//        Region region = regionDao.get(customerInfo.getRegionCode());
//        customerInfo.setRegionString(region.toString());
        return customerInfo;
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
    public List<CustomerInfo> findByParameter(CustomerInfo customerInfo) {
        List<CustomerInfo> customerInfos = new ArrayList<>();
        System.out.println(customerInfo);
        if(customerInfo != null){
            customerInfos = customerInfoDao.fingByParameter(customerInfo);
        }
        return customerInfos;
    }

    @Override
    public int save(CustomerInfo customerInfo) {
        return customerInfoDao.insert(customerInfo);
    }

    @Override
    public int removeById(int id) {
        return customerInfoDao.delete(id);
    }

    @Override
    public int update(CustomerInfo customerInfo) {
        return customerInfoDao.update(customerInfo);
    }

}
