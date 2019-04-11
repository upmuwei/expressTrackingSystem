package com.expresstracking.service.impl;

import com.expresstracking.dao.RegionDao;
import com.expresstracking.dao.TransNodeDao;
import com.expresstracking.entity.TransNode;
import com.expresstracking.service.TransNodeService;
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
public class TransNodeServiceImpl implements TransNodeService {
    @Autowired
    private TransNodeDao transNodeDao;
    @Override
    public TransNode get(String id) {
        return transNodeDao.get(id);
    }
    @Override
    public List<TransNode> findByRegionCodeAndType(String regionCode, int type) {
        return transNodeDao.findByRegionCodeAndType(regionCode,type);
    }
}
