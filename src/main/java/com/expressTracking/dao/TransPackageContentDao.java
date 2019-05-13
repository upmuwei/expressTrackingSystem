package com.expressTracking.dao;

import com.expressTracking.entity.TransPackageContent;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface TransPackageContentDao {
    public int update(TransPackageContent transPackageContent);

    public int updateStatusByPackageId(@Param("packageId") String packageId,@Param("status") Integer status);

    public int insert(TransPackageContent transPackageContent);

    public List<String> getExpressId(String packageId);

    public List<String> getPackageId(String expressId);

    public List<TransPackageContent> findByPackageIdAndStatus(@Param("packageId")String packageId,@Param("status") int status);

    public List<TransPackageContent> getByPackageId(String packageId);

    public TransPackageContent findByExpressIdAndStatus(@Param("expressId") String expressId,
                                                        @Param("status")int status);

    public TransPackageContent findByPackageIdAndEsId(@Param("packageId") String packageId,@Param("esId") String esId);
}
