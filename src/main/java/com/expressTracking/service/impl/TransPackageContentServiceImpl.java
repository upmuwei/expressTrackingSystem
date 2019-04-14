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
 * @author 19231
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

    /**
     *依据sn排序，升序
     */
    @Override
    public TransPackageContent findByExpressIdAndStatus(String expressId, int status) {
        return transPackageContentDao.findByExpressIdAndStatus(expressId, status);
    }

    @Override
    public List<TransPackageContent> findByExpressId(String expressId) {
        return transPackageContentDao.getByExpressId(expressId);
    }

    @Override
    public List<TransPackageContent> findByPackageId(String packageId) {
        return transPackageContentDao.getByPackageId(packageId);
    }


}
