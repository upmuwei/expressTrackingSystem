package com.expresstracking.dao;

import com.expresstracking.entity.UserInfo;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserInfoDao {

    public int update(UserInfo userInfo);

    public int delete(int id);

    public void insert(UserInfo userInfo);

    public UserInfo get(int id);

    public List<UserInfo> getAll();
}
