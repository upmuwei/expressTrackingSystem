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
        this.transPackageContentDao = transPackageContentDao;
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
    public int moveEsToPackage(String packageId, String esId) {
        TransPackageContent transPackageContent = containExpress(packageId, esId);
        if (transPackageContent != null) {
            transPackageContent.setStatus(TransPackageContent.STATUS.STATUS_ACTIVE);
            return transPackageContentDao.update(transPackageContent);
        } else {
            transPackageContent = new TransPackageContent();
            transPackageContent.setExpressId(esId);
            transPackageContent.setPackageId(packageId);
            transPackageContent.setStatus(TransPackageContent.STATUS.STATUS_ACTIVE);
            return transPackageContentDao.insert(transPackageContent);
        }
    }

    @Override
    public int moveEsOutPackage(String packageId, String esId) {
        TransPackageContent transPackageContent = containExpress(packageId, esId);
        if (transPackageContent != null) {
            if (transPackageContent.getStatus() == TransPackageContent.STATUS.STATUS_ACTIVE) {
                transPackageContent.setStatus(TransPackageContent.STATUS.STATUS_OUTOF_PACKAGE);
                return transPackageContentDao.update(transPackageContent);
            }else{
                return 1;
            }
        }
        return 0;
    }

    @Override
    public TransPackageContent findByExpressIdAndStatus(String expressId, int status) {
        return transPackageContentDao.findByExpressIdAndStatus(expressId, status);
    }

    @Override
    public List<TransPackageContent> findByPackageIdAndStatus(String packageId, int status) {
        return transPackageContentDao.findByPackageIdAndStatus(packageId, status);
    }

    @Override
    public TransPackageContent containExpress(String packageId, String esId) {
        return transPackageContentDao.findByPackageIdAndEsId(packageId, esId);
    }
}
