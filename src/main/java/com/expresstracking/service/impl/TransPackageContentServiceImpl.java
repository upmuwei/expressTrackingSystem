package com.expresstracking.service.impl;

import com.expresstracking.dao.TransPackageContentDao;
import com.expresstracking.entity.TransPackageContent;
import com.expresstracking.service.TransPackageContentService;
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

    @Override
    public TransPackageContent get(String id, String sourcePkgId) {
        return null;
    }

    /**
     *
     * @param orderBy 排序依据
     * @param isAsc 是否为升序
     * @param expressSheetId 快件id
     * @param status 包裹内容状态
     * @return 包裹内容集合
     */
    @Override
    public List<TransPackageContent> findBy(String orderBy, boolean isAsc, String expressSheetId, int status) {
        return null;
    }

    @Override
    public List<TransPackageContent> findBy(String propertyName, Object value, String orderBy, boolean isAsc) {
        return null;
    }
}
