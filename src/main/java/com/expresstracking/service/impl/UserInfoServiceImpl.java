package com.expresstracking.service.impl;

import com.expresstracking.entity.UserInfo;
import com.expresstracking.service.UserInfoService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
public class UserInfoServiceImpl implements UserInfoService {
    @Override
    public UserInfo get(int uId) {
        return null;
    }

    @Override
    public void save(UserInfo userInfo) {

    }

    @Override
    public int update(UserInfo userInfo) {
        return 0;
    }

    @Override
    public int delete(int uId) {
        return 0;
    }

    @Override
    public UserInfo checkLogin(int uId, String password) {
        return null;
    }

    @Override
    public List<UserInfo> findLike(String propertyName, Object value, String orderBy, boolean isAsc) {
        return null;
    }

    @Override
    public List<UserInfo> findBy(String propertyName, Object value, String orderBy, boolean isAsc) {
        return null;
    }
}
