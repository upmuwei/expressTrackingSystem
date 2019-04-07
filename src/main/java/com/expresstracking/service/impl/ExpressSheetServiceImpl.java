package com.expresstracking.service.impl;

import com.expresstracking.entity.ExpressSheet;
import com.expresstracking.service.ExpressSheetService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
public class ExpressSheetServiceImpl implements ExpressSheetService {

    @Override
    public int update(ExpressSheet expressSheet) {
        return 0;
    }

    @Override
    public List<ExpressSheet> findBy(String propertyName, Object value, String orderBy, boolean isAsc) {
        return null;
    }

    @Override
    public List<ExpressSheet> findLike(String propertyName, Object value, String orderBy, boolean isAsc) {
        return null;
    }

    @Override
    public List<ExpressSheet> getListInPackage(String packageId) {
        return null;
    }

    @Override
    public ExpressSheet get(String id) {
        return null;
    }

    @Override
    public void save(ExpressSheet expressSheet) {

    }

    @Override
    public int delete(String id) {
        return 0;
    }

    @Override
    public int removeById(String id) {
        return 0;
    }
}
