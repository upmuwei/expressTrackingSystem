package com.expresstracking.service.impl;

import com.expresstracking.entity.UsersPackage;
import com.expresstracking.service.UserPackageService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserPackageServiceImpl implements UserPackageService {

    @Override
    public List<UsersPackage> getTransPackageList(int userUId) {
        return null;
    }

    @Override
    public List<UsersPackage> findBy(String propertyName, Object value, String orderBy, boolean isAsc) {
        return null;
    }

    @Override
    public void save(UsersPackage userPackage) {

    }

    @Override
    public int remove(int sn) {
        return 0;
    }
}
