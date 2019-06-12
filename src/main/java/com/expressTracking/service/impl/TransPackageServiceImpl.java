package com.expressTracking.service.impl;

import com.expressTracking.controller.DomainController;
import com.expressTracking.dao.*;
import com.expressTracking.entity.*;
import com.expressTracking.exception.ServiceException;
import com.expressTracking.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author muwei
 * @date 2019/4/8
 */
@Service("transPackageService")
@Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
public class TransPackageServiceImpl implements TransPackageService {

    private static final Logger LOGGER = LoggerFactory.getLogger(TransPackageServiceImpl.class);
    @Autowired
    private TransPackageDao transPackageDao;
    @Autowired
    private TransPackageContentDao transPackageContentDao;
    @Autowired
    private ExpressSheetDao expressSheetDao;
    @Autowired
    private UserPackageService userPackageService;
    @Autowired
    private UserInfoService userInfoService;
    @Autowired
    private PackageRecordService packageRecordService;
    @Autowired
    private ExpressSheetService esService;

    @Override
    public List<TransPackage> findBy(String propertyName, String value) {
        return transPackageDao.findBy(propertyName, value);
    }

    @Override
    public List<TransPackage> findLike(String propertyName, String value) {
        return transPackageDao.findLike(propertyName, value);
    }

    @Override
    public TransPackage get(String id) {
        return transPackageDao.get(id);
    }

    @Override
    public List<TransPackage> getByUserId(Integer userId, Integer operation, Integer status) {
        return transPackageDao.getByUserId(userId, operation, status);
    }

    @Override
    public int save(TransPackage transPackage) {
        return transPackageDao.insert(transPackage);
    }

    @Override
    public int update(TransPackage transPackage) {
        return transPackageDao.update(transPackage);
    }

    @Override
    public int newTransPackage(TransPackage transPackage, int uId) {
        transPackage.setStatus(TransPackage.PACKAGE_NEW);
        transPackage.setCreateTime(new Date());
        return transPackageDao.insert(transPackage) * packageRecordService.addPackageRecord(transPackage.getId(), uId, PackageRecord.PACKAGE_NEW);
    }

    @Override
    public int newTransPackage(String packageId, int userId) {
        TransPackage transPackage = new TransPackage();
        transPackage.setId(packageId);
        transPackage.setStatus(TransPackage.PACKAGE_NEW);
        transPackage.setCreateTime(new Date());
        return transPackageDao.insert(transPackage) * packageRecordService.addPackageRecord(transPackage.getId(), userId, PackageRecord.PACKAGE_NEW);
    }


    @Override
    public int packTransPackage(String packageId, String expressId) throws Exception {
        TransPackage transPackage = transPackageDao.get(packageId);
        if (transPackage.getStatus() == 1 || transPackage.getStatus() == 2) {
            throw new Exception("包裹状态信息错误");
        }
        if (transPackage.getStatus() == 0) {
            transPackage.setStatus(1);
        }
        if (transPackageDao.update(transPackage) == 0) {
            return 0;
        }
        TransPackageContent transPackageContent = new TransPackageContent();
        transPackageContent.setPackageId(packageId);
        transPackageContent.setExpressId(expressId);
        transPackageContent.setStatus(TransPackageContent.STATUS.STATUS_ACTIVE);
        transPackageContentDao.insert(transPackageContent);
        return 1;
    }
    /*==============================================李伟===================================================*/

    /**
     * 拆包
     * 步骤
     * 1 检查包裹是否存在 存在执行 2 不存在 返回 1
     * 2 检查用户是否存在 是 执行 3 否 返回 2
     * 3 将包裹中的TransPackageContent 置为 STATUS_OUTOF_PACKAGE
     * 4 将包裹中的所有快件状态置为 分拣状态STATUS_PARTATION
     * 5 添加包裹操作记录
     * 6 修改包裹状态
     * 7 返回 3 拆包成功
     *
     * @param packageId
     * @param userId
     * @return 1 包裹不存在 2 用户不存在 3 拆包成功
     */
    @Override
    public int unPackTransPckage(String packageId, int userId) {
        TransPackage transPackage = get(packageId);
        if (transPackage == null) {
            //包裹不存在
            throw new ServiceException(3000);
        } else if (transPackage.getStatus() == TransPackage.PACKAGE_TRANS) {
            throw new ServiceException(3001, "无法拆包");//包裹状态错误
        }

        UserInfo userInfo = userInfoService.get(userId);
        if (userInfo == null) {
            //用户不存在
            throw new ServiceException(1000);
        }

        //将所有快件移出包裹中
        transPackageContentDao.updateStatusByPackageId(packageId, TransPackageContent.STATUS.STATUS_OUTOF_PACKAGE);
        for (TransPackageContent transPackageContent : transPackage.getContent()) {
            //将快件状态置为 分拣状态
            expressSheetDao.updateEsStatus(transPackageContent.getExpressId(), ExpressSheet.STATUS.STATUS_PARTATION);
        }

        //添加包裹操作历史
        packageRecordService.addPackageRecord(packageId, userId, PackageRecord.PACKAGE_UNPACK);
        //修改包裹状态
        transPackage.setStatus(TransPackage.PACKAGE_COMPLETE);
        update(transPackage);
        //成功
        return update(transPackage);
    }


    /**
     * 转运包裹
     *
     * @param packageId 包裹Id
     * @param userId    用户id
     * @return
     * @throws Exception
     */
    @Override
    public int transportPackage(String packageId, int userId) throws Exception {
        //检查用户信息是否存在
        UserInfo userInfo = userInfoService.get(userId);
        if (userInfo == null) {
            throw new ServiceException(1000);
        }
        //检查包裹信息是否存在
        TransPackage transPackage = transPackageDao.get(packageId);
        if (transPackage == null) {
            throw new ServiceException(3000);
        }
        switch (transPackage.getStatus()) {
            case TransPackage.PACKAGE_NEW:
                throw new ServiceException(3001, "包裹未被打包，无法转运");
            case TransPackage.PACKAGE_COMPLETE:
                throw new ServiceException(3001, "包裹已被废弃，无法转运");

        }
        //将包裹设为转运状态
        transPackage.setStatus(TransPackage.PACKAGE_TRANS);

        UsersPackage usersPackage = userPackageService.getUserPackage(packageId, userId);
        if (usersPackage != null) {
            throw new ServiceException(4001, "包裹已经被转运");
        }

        List<ExpressSheet> expressSheetList =esService.getEsListFromPackage(packageId);
        for(int i = 0;i < expressSheetList.size();i++){
            ExpressSheet expressSheet = expressSheetList.get(i);
            expressSheet.setStatus(ExpressSheet.STATUS.STATUS_TRANSPORT);
            expressSheetDao.update(expressSheet);
        }

        //添加UserPackage 和 用户操作包裹的记录 修改包裹状态
        return userPackageService.save(packageId, userId) *
                packageRecordService.addPackageRecord(packageId, userId, PackageRecord.PACKAGE_TRANS) *
                transPackageDao.update(transPackage);
    }

    @Override
    public int receivePackage(String packageId, int userId) throws Exception {
        //检查用户信息是否存在
        UserInfo userInfo = userInfoService.get(userId);
        if (userInfo == null) {
            throw new ServiceException(1000);
        }

        //检查包裹信息是否存在
        TransPackage transPackage = transPackageDao.get(packageId);
        if (transPackage == null) {
            throw new ServiceException(3000);
        }

        if (transPackage.getStatus() != TransPackage.PACKAGE_TRANS) {
            throw new ServiceException(3001, "包裹没有被转运");
        }
        transPackage.setStatus(TransPackage.PACKAGE_SORTING); //将包裹状态修改为分拣状态

        UsersPackage usersPackage = userPackageService.getUserPackage(packageId, null);
        if (usersPackage == null) {
            throw new ServiceException(3002);
        }
        return transPackageDao.update(transPackage) *
                userPackageService.remove(packageId) *
                packageRecordService.addPackageRecord(packageId, userId, PackageRecord.PACKAGE_RECEIVE);
    }

    /**
     * 获取正在转运的包裹信息
     *
     * @param userId
     * @return
     */
    @Override
    public List<TransPackage> getTransPackage(Integer userId) throws Exception {
        List<UsersPackage> usersPackageList = userPackageService.getUserPackageList(null, userId);
        List<TransPackage> transPackageList = new ArrayList<>();
        if (usersPackageList != null && !usersPackageList.isEmpty()) {
            for (UsersPackage usersPackage : usersPackageList) {
                transPackageList.add(transPackageDao.get(usersPackage.getPackageId()));
            }
        }
        return transPackageList;
    }

    @Override
    public List<TransPackage> getRecevicedPackage(Integer userId) throws Exception {

        return null;
    }

    /**
     * 移出正在转运的包裹
     *
     * @param userId
     * @param packageId
     * @return
     */
    @Override
    public int removeTransportingPackage(Integer userId, String packageId) throws Exception {
        return userPackageService.remove(packageId);
    }
}
