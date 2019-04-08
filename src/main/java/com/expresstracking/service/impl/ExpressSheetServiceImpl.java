package com.expresstracking.service.impl;

import com.expresstracking.dao.ExpressSheetDao;
import com.expresstracking.entity.ExpressSheet;
import com.expresstracking.service.ExpressSheetService;
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
public class ExpressSheetServiceImpl implements ExpressSheetService {
    @Autowired
    private ExpressSheetDao expressSheetDao;
    @Override
    public int update(ExpressSheet expressSheet) {
        return expressSheetDao.update(expressSheet);
    }

    @Override
    public List<ExpressSheet> findBy(String propertyName, Object value, String orderBy, boolean isAsc) {
        return expressSheetDao.findBy(propertyName,value,orderBy,isAsc);
    }

    @Override
    public List<ExpressSheet> findLike(String propertyName, Object value, String orderBy, boolean isAsc) {
        return expressSheetDao.findLike(propertyName,value,orderBy,isAsc);
    }

    @Override
    public List<ExpressSheet> getListInPackage(String packageId) {
        return expressSheetDao.getListInPackage(packageId);
    }

    @Override
    public ExpressSheet get(String id) {
        return expressSheetDao.get(id);
    }

    @Override
    public void save(ExpressSheet expressSheet) {
        expressSheetDao.insert(expressSheet);
    }

    @Override
    public int delete(String id) {
        return expressSheetDao.delete(id);
    }

    @Override
    public int removeById(String id) {
        return expressSheetDao.delete(id);
    }
}
