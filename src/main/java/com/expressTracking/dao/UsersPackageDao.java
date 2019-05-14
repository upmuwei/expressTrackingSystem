package com.expressTracking.dao;

import com.expressTracking.entity.UsersPackage;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import javax.jws.soap.SOAPBinding;
import java.util.List;

@Repository
public interface UsersPackageDao {

    public int delete(int sn);

    public int insert(UsersPackage userPackage);

    public List<UsersPackage> getByUserUId(int userUId);

    public UsersPackage getByPackageId(@Param("packageId") String packageId);


    /*==============================================李伟===========================================================*/

    public List<UsersPackage> get(@Param("packageId") String packageId,@Param("userId") Integer userId);

    public int remove(String pakcageId);

}
