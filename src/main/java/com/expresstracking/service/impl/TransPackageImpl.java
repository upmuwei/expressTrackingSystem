package com.expresstracking.service.impl;

import com.expresstracking.dao.TransPackageDao;
import com.expresstracking.entity.TransPackage;
import com.expresstracking.service.TransPackageService;
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
    @Autowired
    private TransPackageDao transPackageDao;
    @Override
    public List<TransPackage> findBy(String propertyName, Object value, String orderBy, boolean isAsc) {
        return null;
    }

    @Override
    public List<TransPackage> findLike(String propertyName, Object value, String orderBy, boolean isAsc) {
        return null;
    }

    @Override
    public List<TransPackage> getListInPackage(String packageId) {
        return null;
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
