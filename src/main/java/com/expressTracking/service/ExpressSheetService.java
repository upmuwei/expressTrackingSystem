package com.expressTracking.service;

import com.expressTracking.entity.ExpressSheet;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

/**
 * @author muwei
 */
public interface ExpressSheetService {


    public int update(ExpressSheet expressSheet);

    public int updateEsStatus(String esId,int statsu);

    public int delete(String esId);

    public List<ExpressSheet> findBy(String propertyName, String value);

    public List<ExpressSheet> findLike(String propertyName, String value);

    /**
     * 查询在包裹中的快件信息
     * @param packageId
     * @return
     */
    public List<ExpressSheet> getListInPackage(String packageId);

    public ExpressSheet get(String id);

    public List<ExpressSheet> getByParameters(ExpressSheet expressSheet);

    public List<ExpressSheet> getByAccpterAndStatus(String accepter, Integer status);

    /**
     * 创建快件信息
     *
     * @param expressId
     * @param accepter
     * @return
     */
    public int create(String expressId, Integer accepter);


    public int save(ExpressSheet expressSheet);

    /**
     * 获取包裹中的快件列表
     *
     * @param pckageId
     * @return
     */
    public List<ExpressSheet> getEsListFromPackage(String pckageId);

    /**
     * 揽收快递
     *
     * @param expressId 快递单号
     * @param uId       员工Id
     * @return 成功返回1，失败返回0
     */
    public int receiveExpressSheet(String expressId, int uId) throws Exception;

    /**
     * 派送快递
     *
     * @param expressId 快递单号
     * @param uId       员工Id
     * @return 成功返回1，失败返回0
     */
    public int dispatchExpressSheet(String expressId, int uId) throws Exception;

    /**
     * 交付快递
     *
     * @param expressId 快递单号
     * @param uId       员工Id
     * @return 成功返回1，失败返回0
     */
    public int deliveryExpressSheet(String expressId, int uId) throws Exception;


    /**
     * 创建包裹信息
     *
     * @param expressSheet
     * @param accpter
     * @return
     */
    public int create(ExpressSheet expressSheet, Integer accpter);

}
