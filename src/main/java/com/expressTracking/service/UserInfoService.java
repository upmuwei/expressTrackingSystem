package com.expressTracking.service;

import com.expressTracking.entity.UserInfo;
import java.util.List;

public interface UserInfoService {

    public UserInfo get(int uId);

    public void save(UserInfo userInfo) throws Exception;

    public int update(UserInfo userInfo);

    public int delete(int uId);

    public UserInfo checkLogin(int uId, String password);

    public List<UserInfo> findLike(String propertyName, String value);

    public List<UserInfo> findBy(String propertyName, String value);

}
