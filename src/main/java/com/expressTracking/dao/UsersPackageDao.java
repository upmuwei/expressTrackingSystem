package com.expressTracking.dao;

import com.expressTracking.entity.UsersPackage;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import javax.jws.soap.SOAPBinding;
import java.util.List;

@Repository
public interface UsersPackageDao {

    public int delete(int sn);

    public void insert(UsersPackage userPackage);

    public List<UsersPackage> getByUserUId(int userUId);

    public UsersPackage getByPackageId(String packageId);

    public List<UsersPackage> findBy(@Param("propertyName") String propertyName,
                                     @Param("value") String value);

}
