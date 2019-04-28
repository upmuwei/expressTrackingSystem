package com.expressTracking.dao;

import com.expressTracking.entity.UserInfo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserInfoDao {

    public int update(UserInfo userInfo);

    public int delete(int uId);

    public void insert(UserInfo userInfo);

    public UserInfo get(int uId);

    public int checkByTelCode(String telCode);

    public UserInfo checkLogin(@Param("telCode") String telCode);

    /**
     *默认依据id排序，升序
     */
    public List<UserInfo> findLike(@Param("propertyName") String propertyName,
                                   @Param("value") String value);

    public List<UserInfo> findBy(@Param("propertyName") String propertyName,
                                 @Param("value") String value);

}
