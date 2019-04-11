package com.expresstracking.service.impl;

import com.expresstracking.dao.ExpressSheetDao;
import com.expresstracking.dao.TransHistoryDao;
import com.expresstracking.entity.ExpressSheet;
import com.expresstracking.entity.TransHistory;
import com.expresstracking.service.TransHistoryService;
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
public class TransHistoryServiceImpl implements TransHistoryService {
    private final TransHistoryDao transHistoryDao;
    private ExpressSheetDao expressSheetDao;
    @Autowired
    public TransHistoryServiceImpl(ExpressSheetDao expressSheetDao, TransHistoryDao transHistoryDao) {
        this.expressSheetDao = expressSheetDao;
        this.transHistoryDao = transHistoryDao;
    }

    @Override
    public String appointTransPorter(String packageId, int nodeUId, int userId) {
        return null;
    }

    @Override
    public TransHistory appointDeliver(String transPackageId, String expressSheetId) {
        return null;
    }

    @Override
    public List<TransHistory> getTransHistory(String expressSheetId) {
        ExpressSheet expressSheet=expressSheetDao.get(expressSheetId);
        return null;
    }

    @Override
    public void save(TransHistory transHistory) {
        transHistoryDao.insert(transHistory);
    }
}
