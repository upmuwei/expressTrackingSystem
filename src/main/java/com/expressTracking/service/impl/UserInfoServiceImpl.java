package com.expressTracking.service.impl;

import com.expressTracking.dao.TransPackageDao;
import com.expressTracking.dao.UserInfoDao;
import com.expressTracking.entity.TransPackage;
import com.expressTracking.entity.UserInfo;
import com.expressTracking.service.UserInfoService;
import com.expressTracking.utils.MD5Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

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
    public List<UserInfo> getAll() {
        return userInfoDao.getAll();
    }
    @Override
    public int save(UserInfo userInfo) throws Exception {
        if (userInfoDao.checkByTelCode(userInfo.getTelCode()) == null) {
            String receivePackageId = System.currentTimeMillis() + userInfo.getTelCode();
            TransPackage receivePackage = new TransPackage(receivePackageId, "0",
                    "0", new Date(), 3);
            transPackageDao.insert(receivePackage);
            String delivePackageId = System.currentTimeMillis() + userInfo.getTelCode();
            TransPackage delivePackage = new TransPackage(delivePackageId, "0",
                    "0", new Date(), 5);
            transPackageDao.insert(delivePackage);
            String transPackageId = System.currentTimeMillis() + userInfo.getTelCode();
            TransPackage transPackage = new TransPackage(transPackageId, "0",
                    "0", new Date(), 4);
            transPackageDao.insert(transPackage);
            userInfo.setPassword(MD5Utils.getSaltMD5(userInfo.getPassword()));
            userInfo.setReceivePackageId(receivePackageId);
            userInfo.setDelivePackageId(delivePackageId);
            userInfo.setTransPackageId(transPackageId);
            userInfoDao.insert(userInfo);
            if (userInfo.getuId() == 0) {
                throw new Exception("创建失败");
            }
            return 1;
        }
        return 0;
    }

    @Override
    public int update(UserInfo userInfo) {
        if (userInfo != null&& userInfo.getPassword() != null) {
            userInfo.setPassword(MD5Utils.getSaltMD5(userInfo.getPassword()));
        }
        return userInfoDao.update(userInfo);
    }

    @Override
    public int updatePassword(int userId, String password) {
        UserInfo userInfo = new UserInfo();
        userInfo.setuId(userId);
        userInfo.setPassword(password);
        return update(userInfo);
    }

    @Override
    public int delete(int uId) {
        return userInfoDao.delete(uId);
    }

    @Override
    public UserInfo checkLogin(String account, String password) {
        UserInfo userInfo = userInfoDao.checkLogin(account);
        System.out.println(userInfo);
        if (userInfo == null) {
            return null;
        } else if (MD5Utils.getSaltverifyMD5(password, userInfo.getPassword())) {
//            userInfo.setPassword(password);
            return userInfo;
        }
        return null;
    }

    @Override
    public List<UserInfo> findLike(String propertyName, String value) {
        return userInfoDao.findLike(propertyName, value);
    }

    @Override
    public List<UserInfo> findBy(String propertyName, String value) {
        return userInfoDao.findBy(propertyName, value);
    }

    @Override
    public UserInfo getUserByTelCode(String phone) {
        List<UserInfo> userInfos = findBy("TelCode", phone);
        if (!userInfos.isEmpty()) {
            return userInfos.get(0);
        } else {
            return null;
        }
    }
}
