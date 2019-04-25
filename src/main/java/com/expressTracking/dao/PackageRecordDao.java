package com.expressTracking.dao;

import com.expressTracking.entity.PackageRecord;
import org.springframework.stereotype.Repository;
import java.util.List;

/**
 * @author muwei
 * @date 2019/4/25
 */
@Repository
public interface PackageRecordDao {

    public void insert(PackageRecord packageRecord);

    public List<PackageRecord> selectByuId(int uId);

    public List<PackageRecord> selectByPackageId(String packageId);


}
