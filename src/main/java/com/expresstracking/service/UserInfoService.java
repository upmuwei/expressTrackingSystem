package com.expresstracking.service;

import com.expresstracking.entity.UserInfo;

import java.util.List;

public interface UserInfoService {

    public UserInfo get(int uId);

    public void save(UserInfo userInfo);

    public int update(UserInfo userInfo);

    public int delete(int uId);

    public UserInfo checkLogin(int uId, String password);

    public List<UserInfo> findLike(String propertyName, Object value, String orderBy, boolean isAsc);

    public List<UserInfo> findBy(String propertyName, Object value, String orderBy, boolean isAsc);
}
