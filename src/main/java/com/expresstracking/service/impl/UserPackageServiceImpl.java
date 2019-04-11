package com.expresstracking.service.impl;

import com.expresstracking.dao.TransPackageContentDao;
import com.expresstracking.dao.UsersPackageDao;
import com.expresstracking.entity.UsersPackage;
import com.expresstracking.service.UserPackageService;
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
        List<UsersPackage> usersPackages=usersPackageDao.get(userUId);
        return usersPackages;
    }

    @Override
    public List<UsersPackage> findBy(String propertyName, Object value, String orderBy, boolean isAsc) {
        return null;
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
