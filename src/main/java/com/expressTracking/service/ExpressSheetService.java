package com.expressTracking.service;

import com.expressTracking.entity.ExpressSheet;

import java.util.List;

/**
 * @author muwei
 */
public interface ExpressSheetService {

    public int update(ExpressSheet expressSheet);

    public List<ExpressSheet> findBy(String propertyName, String value);

    public List<ExpressSheet> findLike(String propertyName, String value);

    public List<ExpressSheet> getListInPackage(String packageId);

    public ExpressSheet get(String id);

    public int save(ExpressSheet expressSheet) throws Exception;

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
