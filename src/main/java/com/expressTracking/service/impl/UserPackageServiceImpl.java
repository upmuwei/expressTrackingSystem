package com.expressTracking.service.impl;

import com.expressTracking.dao.TransHistoryDao;
import com.expressTracking.dao.TransNodeDao;
import com.expressTracking.dao.UserInfoDao;
import com.expressTracking.dao.UsersPackageDao;
import com.expressTracking.entity.TransHistory;
import com.expressTracking.entity.TransNode;
import com.expressTracking.entity.UserInfo;
import com.expressTracking.entity.UsersPackage;
import com.expressTracking.service.TransHistoryService;
import com.expressTracking.service.TransNodeService;
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

    private final TransNodeDao transNodeDao;

    @Autowired
    public UserPackageServiceImpl(UsersPackageDao usersPackageDao,
                                  UserInfoDao userInfoDao,
                                  TransHistoryDao transHistoryDao, TransNodeDao transNodeDao) {
        this.usersPackageDao = usersPackageDao;
        this.userInfoDao = userInfoDao;
        this.transHistoryDao = transHistoryDao;
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
}
