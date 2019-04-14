package com.expressTracking.service.impl;

import com.expressTracking.dao.TransPackageDao;
import com.expressTracking.entity.TransPackage;
import com.expressTracking.service.TransPackageService;
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
public class TransPackageImpl implements TransPackageService {
    private final TransPackageDao transPackageDao;

    @Autowired
    public TransPackageImpl(TransPackageDao transPackageDao) {
        this.transPackageDao = transPackageDao;
    }

    @Override
    public List<TransPackage> findBy(String propertyName, String value) {
        return transPackageDao.findBy(propertyName, value);
    }

    @Override
    public List<TransPackage> findLike(String propertyName, String value) {
        return transPackageDao.findLike(propertyName, value);
    }

    @Override
    public TransPackage get(String id) {
        return transPackageDao.get(id);
    }

    @Override
    public void save(TransPackage transPackage) {
        transPackageDao.insert(transPackage);
    }
    @Override
    public void update(TransPackage transPackage) {
        transPackageDao.update(transPackage);
    }
}
