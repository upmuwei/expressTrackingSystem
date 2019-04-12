package com.expresstracking.service.impl;

import com.expresstracking.dao.ExpressSheetDao;
import com.expresstracking.dao.TransHistoryDao;
import com.expresstracking.dao.TransPackageContentDao;
import com.expresstracking.entity.TransHistory;
import com.expresstracking.service.TransHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 19231
 * @date 2019/4/8
 */
@Service
@Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
public class TransHistoryServiceImpl implements TransHistoryService {
    private final TransHistoryDao transHistoryDao;
    private final TransPackageContentDao transPackageContentDao;

    @Autowired
    public TransHistoryServiceImpl(TransHistoryDao transHistoryDao,
                                   TransPackageContentDao transPackageContentDao) {
        this.transHistoryDao = transHistoryDao;
        this.transPackageContentDao = transPackageContentDao;
    }


//    @Override
//    public String appointTransPorter(String packageId, int nodeUId, int userId) {
//        return null;
//    }
//
//    @Override
//    public TransHistory appointDeliver(String transPackageId, String expressSheetId) {
//        return null;
//    }

    @Override
    public List<TransHistory> getTransHistory(String expressSheetId) {

        List<String> packageIdList = transPackageContentDao.getPackageId(expressSheetId);
        List<TransHistory> transHistoryList = new ArrayList<>();
        for(String packageId:packageIdList){
            transHistoryList.add(transHistoryDao.get(packageId));
        }
        return transHistoryList;
    }

    @Override
    public void save(TransHistory transHistory) {
        transHistoryDao.insert(transHistory);
    }
}
