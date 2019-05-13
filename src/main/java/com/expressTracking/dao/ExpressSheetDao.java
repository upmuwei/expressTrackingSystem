package com.expressTracking.dao;

import com.expressTracking.entity.ExpressSheet;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Repository
public interface ExpressSheetDao {

    public int update(ExpressSheet expressSheet);

    /**
     * 修改包裹状态
     * @param esId
     * @param status
     * @return
     */
    public int updateEsStatus(@Param("esId") String esId, @Param("status") int status);

    public int delete(String id);

    public int insert(ExpressSheet expressSheet);

    public List<ExpressSheet> selectEsByPackageId(String packageId);

    public ExpressSheet get(String id);

    public List<ExpressSheet> findBy(@Param("propertyName") String propertyName,
                                     @Param("value") String value);

    public List<ExpressSheet> findLike(@Param("propertyName") String propertyName,
                                       @Param("value") String value);
    public List<ExpressSheet> getByParameters(ExpressSheet expressSheet);



    public List<ExpressSheet> findByMoreConditions(ExpressSheet expressSheet);
}
