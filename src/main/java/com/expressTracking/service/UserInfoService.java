package com.expressTracking.service;

import com.expressTracking.entity.UserInfo;
import java.util.List;

/**
 * @author muwei
 * @date 2019/4/28
 */
public interface UserInfoService {

    public UserInfo get(int uId);

    public int save(UserInfo userInfo) throws Exception;

    public int update(UserInfo userInfo);

    public int delete(int uId);

    public UserInfo checkLogin(String account, String password);

    public List<UserInfo> findLike(String propertyName, String value);

    public List<UserInfo> findBy(String propertyName, String value);

}
