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

    /**
     * 查找快件
     * @param propertyName 属性名称
     * @param value 属性值
     * @param orderBy 排序依据
     * @param isAsc 是否为升序
     * @return List<ExpressSheet>
     */
    public List<ExpressSheet> findBy(@Param("propertyName") String propertyName,
                                     @Param("value") Object value, @Param("orderBy") String orderBy,
                                     @Param("isAsc") boolean isAsc);

    /**
     * 查找快件
     * @param propertyName 属性名称
     * @param value 属性值
     * @param orderBy 排序依据
     * @param isAsc 是否为升序
     * @return List<ExpressSheet>
     */
    public List<ExpressSheet> findLike(@Param("propertyName") String propertyName, @Param("value") Object value,
                                       @Param("orderBy") String orderBy, @Param("isAsc") boolean isAsc);

}
