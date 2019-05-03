package com.expressTracking.service;

import com.expressTracking.entity.ExpressSheet;
import java.util.List;

/**
 * @author muwei
 * @date 2019/4/28
 */
public interface ExpressSheetService {

    public int update(ExpressSheet expressSheet);

    public List<ExpressSheet> findBy(String propertyName, String value);

    public List<ExpressSheet> findLike(String propertyName, String value);

    public List<ExpressSheet> getListInPackage(String packageId);

    public ExpressSheet getByExpressId(String id);

    public int save(ExpressSheet expressSheet) throws Exception;

    /**
     * 根据多种条件查询快递
     * @param expressSheet 快递单
     * @return 快递集合
     */
    public List<ExpressSheet> getByMoreConditions(ExpressSheet expressSheet);

    /**
     * 揽收快递
     * @param expressId 快递单号
     * @param uId 员工Id
     * @return 成功返回1，失败返回0
     */
    public int receiveExpressSheet(String expressId, int uId) throws Exception;

    /**
     * 派送快递
     * @param expressId 快递单号
     * @param uId 员工Id
     * @return 成功返回1，失败返回0
     */
    public int dispatchExpressSheet(String expressId, int uId) throws Exception;

    /**
     * 交付快递
     * @param expressId 快递单号
     * @param uId 员工Id
     * @return 成功返回1，失败返回0
     */
    public int deliveryExpressSheet(String expressId, int uId) throws Exception;
}
