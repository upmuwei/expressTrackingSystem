package com.expresstracking.service.impl;

import com.expresstracking.entity.TransHistory;
import com.expresstracking.service.TransHistoryService;
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
        return null;
    }

    @Override
    public void save(TransHistory transHistory) {

    }
}
