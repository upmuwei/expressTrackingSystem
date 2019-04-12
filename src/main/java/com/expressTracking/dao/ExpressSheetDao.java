package com.expressTracking.dao;

import com.expressTracking.entity.ExpressSheet;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExpressSheetDao {

    public int update(ExpressSheet expressSheet);

    public int delete(String id);

    public void insert(ExpressSheet expressSheet);

    public ExpressSheet get(String id);

    //public List<ExpressSheet> getAll();

    //具有多个参数都按照这种形式改一下
    public List<ExpressSheet> findBy(@Param("propertyName") String propertyName,
                                     @Param("value") Object value, @Param("orderBy") String orderBy,
                                     @Param("isAsc") boolean isAsc);

    public List<ExpressSheet> findLike(String propertyName, Object value, String orderBy, boolean isAsc);

}
