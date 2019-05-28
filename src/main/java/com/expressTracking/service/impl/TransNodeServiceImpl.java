package com.expressTracking.service.impl;

import com.expressTracking.dao.TransNodeDao;
import com.expressTracking.entity.TransNode;
import com.expressTracking.service.TransNodeService;
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
@Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
public class TransNodeServiceImpl implements TransNodeService {

    private final TransNodeDao transNodeDao;

    @Autowired
    public TransNodeServiceImpl(TransNodeDao transNodeDao) {
        this.transNodeDao = transNodeDao;
    }

    @Override
    public List<TransNode> getByParameters(TransNode transNode) {
        return transNodeDao.getByParameters(transNode);
    }

    @Override
    public int save(TransNode transNode) {
        return transNodeDao.insert(transNode);
    }

    @Override
    public int update(TransNode transNode) {
        return transNodeDao.update(transNode);
    }

    @Override
    public TransNode get(String transNodeId) {
        return transNodeDao.getById(transNodeId);
    }
}
