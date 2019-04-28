package com.expressTracking.service.impl;

import com.expressTracking.dao.*;
import com.expressTracking.entity.*;
import com.expressTracking.service.UserPackageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * @author muwei
 * @date 2019/4/28
 */
@Service
@Transactional(isolation = Isolation.READ_COMMITTED,
        propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
public class UserPackageServiceImpl implements UserPackageService {
    private final UsersPackageDao usersPackageDao;

    private final UserInfoDao userInfoDao;

    private final TransHistoryDao transHistoryDao;

    private final TransPackageDao transPackageDao;

    private final TransNodeDao transNodeDao;

    @Autowired
    public UserPackageServiceImpl(UsersPackageDao usersPackageDao,
                                  UserInfoDao userInfoDao,
                                  TransHistoryDao transHistoryDao,
                                  TransPackageDao transPackageDao, TransNodeDao transNodeDao) {
        this.usersPackageDao = usersPackageDao;
        this.userInfoDao = userInfoDao;
        this.transHistoryDao = transHistoryDao;
        this.transPackageDao = transPackageDao;
        this.transNodeDao = transNodeDao;
    }

    @Override
    public List<UsersPackage> getTransPackageList(int userUId) {
        return usersPackageDao.getByUserUId(userUId);
    }

    @Override
    public UsersPackage findByPackageId(String packageId) {
        return usersPackageDao.getByPackageId(packageId);
    }

    @Override
    public void save(UsersPackage userPackage) {
        usersPackageDao.insert(userPackage);
    }

    @Override
    public int remove(int sn) {
        return usersPackageDao.delete(sn);
    }

    @Override
    public int arriveDestination(String packageId, int userId1, int userId2) throws Exception {
        UsersPackage usersPackage = usersPackageDao.getByPackageId(packageId);
        UserInfo userInfo = userInfoDao.get(userId2);
        TransNode transNode = transNodeDao.get(userInfo.getDptId());
        if (usersPackage == null) {
            throw new Exception("未查到包裹Id");
        }
        if(usersPackageDao.delete(usersPackage.getSn()) == 0) {
            return 0;
        }
        usersPackage.setUserUid(userId2);
        usersPackage.setSn(0);
        usersPackageDao.insert(usersPackage);
        TransHistory transHistory = new TransHistory(packageId,
                new Date(), userId1, userId2, transNode.getX() ,transNode.getY());
        transHistoryDao.insert(transHistory);
        return 1;
    }

    @Override
    public int appointTransPorter(String packageId, int nodeUId, int userId) throws Exception {

        UsersPackage usersPackage = usersPackageDao.getByPackageId(packageId);
        TransPackage transPackage = transPackageDao.get(packageId);
        UserInfo userInfo = userInfoDao.get(nodeUId);
        TransNode transNode = transNodeDao.get(userInfo.getDptId());
        if (usersPackage == null) {
            throw new Exception("包裹id不存在");
        }else if (transPackage.getStatus() == 2) {
            throw  new Exception("包裹处于转运状态，不能为其指派转运员");
        }
        TransHistory transHistory = new TransHistory(packageId, new Date(), nodeUId,
                userId, transNode.getX(), transNode.getY());
        if (usersPackageDao.delete(usersPackage.getSn()) == 0) {
            return 0;
        }
        transHistoryDao.insert(transHistory);
        usersPackage.setUserUid(userId);
        usersPackage.setSn(0);
        usersPackageDao.insert(usersPackage);
        return 1;
    }
}
