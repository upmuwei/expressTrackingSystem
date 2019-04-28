package com.expressTracking.service.impl;

import com.expressTracking.dao.TransPackageDao;
import com.expressTracking.dao.UserInfoDao;
import com.expressTracking.entity.ExpressSheet;
import com.expressTracking.entity.TransPackage;
import com.expressTracking.entity.UserInfo;
import com.expressTracking.service.TransPackageService;
import com.expressTracking.service.UserInfoService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @author muwei
 * @date 2019/4/8
 */
@Service
@Transactional(isolation = Isolation.READ_COMMITTED,
        propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
public class UserInfoServiceImpl implements UserInfoService {
    private final UserInfoDao userInfoDao;

    private final TransPackageDao transPackageDao;

    @Autowired
    public UserInfoServiceImpl(UserInfoDao userInfoDao, TransPackageDao transPackageDao) {
        this.userInfoDao = userInfoDao;
        this.transPackageDao = transPackageDao;
    }


    @Override
    public UserInfo get(int uId) {
        return userInfoDao.get(uId);
    }

    @Override
    public void save(UserInfo userInfo) throws Exception {
        if (userInfoDao.checkByTelCode(userInfo.getTelCode()) == 0) {
            String receivePackageId = '3' + System.currentTimeMillis() + userInfo.getTelCode();
            String delivePackageId = '5' + System.currentTimeMillis() + userInfo.getTelCode();
            String transPackageId = '4' + System.currentTimeMillis() + userInfo.getTelCode();
            userInfo.setReceivePackageId(receivePackageId);
            userInfo.setDelivePackageId(delivePackageId);
            userInfo.setTransPackageId(transPackageId);
            userInfoDao.insert(userInfo);
            TransPackage receivePackage = new TransPackage(receivePackageId, "0",
                    "0", new Date(), 3);
            TransPackage delivePackage = new TransPackage(delivePackageId, "0",
                    "0", new Date(), 5);
            TransPackage transPackage = new TransPackage(transPackageId, "0",
                    "0", new Date(), 4);
            transPackageDao.insert(receivePackage);
            transPackageDao.insert(delivePackage);
            transPackageDao.insert(transPackage);
            return;
        }
        throw new Exception("此用户已存在");
    }

    @Override
    public int update(UserInfo userInfo) {
        return userInfoDao.update(userInfo);
    }

    @Override
    public int delete(int uId) {
        return userInfoDao.delete(uId);
    }

    @Override
    public UserInfo checkLogin(String account, String password) {
        return userInfoDao.checkLogin(account,password);
    }

    @Override
    public List<UserInfo> findLike(String propertyName, String value) {
        return userInfoDao.findLike(propertyName,value);
    }

    @Override
    public List<UserInfo> findBy(String propertyName, String value) {
        return userInfoDao.findBy(propertyName,value);
    }
}
