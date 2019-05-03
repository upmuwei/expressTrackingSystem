package com.expressTracking.dao;

import com.expressTracking.entity.ExpressSheet;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExpressSheetDao {

    public int update(ExpressSheet expressSheet);

    public int delete(String id);

    public int insert(ExpressSheet expressSheet);

    public ExpressSheet get(String id);

    public List<ExpressSheet> findBy(@Param("propertyName") String propertyName,
                                     @Param("value") String value);

    public List<ExpressSheet> findLike(@Param("propertyName") String propertyName,
                                       @Param("value") String value);


    public List<ExpressSheet> findByMoreConditions(ExpressSheet expressSheet);
}
