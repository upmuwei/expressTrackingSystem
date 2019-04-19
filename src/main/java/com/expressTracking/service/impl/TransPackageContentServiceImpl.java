package com.expressTracking.service.impl;

import com.expressTracking.dao.TransPackageContentDao;
import com.expressTracking.entity.TransPackageContent;
import com.expressTracking.service.TransPackageContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author muwei
 * @date 2019/4/8
 */
@Service
@Transactional(isolation = Isolation.READ_COMMITTED,
        propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
public class TransPackageContentServiceImpl implements TransPackageContentService {

    private TransPackageContentDao transPackageContentDao;

    @Autowired
    public TransPackageContentServiceImpl(TransPackageContentDao transPackageContentDao) {
        this.transPackageContentDao=transPackageContentDao;
    }

    @Override
    public void save(TransPackageContent transPackageContent) {
        transPackageContentDao.insert(transPackageContent);
    }

    @Override
    public int update(TransPackageContent transPackageContent) {
        return transPackageContentDao.update(transPackageContent);
    }

    @Override
    public TransPackageContent findByExpressIdAndStatus(String expressId, int status) {
        return transPackageContentDao.findByExpressIdAndStatus(expressId, status);
    }

    @Override
    public List<TransPackageContent> findByPackageIdAndStatus(String packageId, int status) {
        return transPackageContentDao.findByPackageIdAndStatus(packageId, status);
    }

}
