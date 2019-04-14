package com.expressTracking.service.impl;

import com.expressTracking.dao.UsersPackageDao;
import com.expressTracking.entity.UsersPackage;
import com.expressTracking.service.UserPackageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
public class UserPackageServiceImpl implements UserPackageService {
    private UsersPackageDao usersPackageDao;

    @Autowired
    public UserPackageServiceImpl(UsersPackageDao usersPackageDao) {
        this.usersPackageDao = usersPackageDao;
    }

    @Override
    public List<UsersPackage> getTransPackageList(int userUId) {
        return usersPackageDao.getByUserUId(userUId);
    }

    @Override
    public List<UsersPackage> findBy(String propertyName, String value) {
        return usersPackageDao.findBy(propertyName, value);
    }

    @Override
    public void save(UsersPackage userPackage) {
        usersPackageDao.insert(userPackage);
    }

    @Override
    public int remove(int sn) {
        return usersPackageDao.delete(sn);
    }
}
