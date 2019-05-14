package com.expressTracking.service.impl;

import com.expressTracking.dao.*;
import com.expressTracking.entity.*;
import com.expressTracking.exception.ServiceException;
import com.expressTracking.service.*;
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
@Service
@Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
public class PackageRouteServiceImpl implements PackageRouteService {

    @Autowired
    private PackageRouteDao packageRouteDao;
    @Autowired
    private TransPackageContentService transPackageContentService;
    @Autowired
    private UserInfoService userInfoService;
    @Autowired
    private UserPackageService userPackageService;
    @Autowired
    private TransPackageService transPackageService;
    @Autowired
    private ExpressSheetService expressSheetService;

    /**
     * 添加包裹位置信息
     *
     * @param userId
     * @param x
     * @param y
     * @param time
     * @return
     */
    @Override
    public int save(Integer userId, float x, float y, Date time) {
        UserInfo userInfo = userInfoService.get(userId);
        if (userInfo == null) {
            throw new ServiceException(1000, "用户不存在");
        }
        //检查用户是否拥有正在转运的包裹
        List<UsersPackage> usersPackages = userPackageService.getTransPackageList(userId);
        if (usersPackages == null && usersPackages.isEmpty()) {
            throw new ServiceException(4000, "用户没有正在转运包裹");
        }
        //添加包裹位置信息
        List<PackageRoute> routeList = new ArrayList<>();
        for (UsersPackage usersPackage : usersPackages) {
            PackageRoute route = new PackageRoute();
            route.setTm(time == null ? new Date() : time);
            route.setPackageId(usersPackage.getPackageId());
            route.setX(x);
            route.setY(y);
            routeList.add(route);
        }
        return save(routeList);
    }

    /**
     * 批量添加位置信息
     *
     * @param routes
     * @return
     */
    @Override
    public int save(List<PackageRoute> routes) {
        if (routes != null && !routes.isEmpty()) {
            return packageRouteDao.insertList(routes);
        }
        return 0;
    }

    /**
     * 保存单个位置信息
     *
     * @param route
     * @return
     */
    @Override
    public int save(PackageRoute route) {
        if (route != null) {
            return packageRouteDao.insert(route);
        }
        return 0;
    }

    /**
     * 删除包裹位置信息
     *
     * @param routeId
     * @return
     */
    @Override
    public int remove(int routeId) {
        return packageRouteDao.delete(routeId);
    }

    @Override
    public int remove(String packageId) {
        return packageRouteDao.deleteByPackageid(packageId);
    }

    /**
     * 修改包裹位置信息
     *
     * @param route
     * @return
     */
    @Override
    public int update(PackageRoute route) {
        if (route != null) {
            return packageRouteDao.update(route);
        }
        return 0;
    }

    /**
     * 通过包裹编号查找包裹的位置信息
     *
     * @param packageId
     * @return
     */
    @Override
    public List<PackageRoute> getPackageRouteList(String packageId) {
        //检查包裹信息是否存在
        TransPackage transPackage = transPackageService.get(packageId);
        if (transPackage == null) {
            throw new ServiceException(4000, "包裹不存在");
        }
        List<PackageRoute> packageRoutes = packageRouteDao.getByPackageId(packageId);
        return packageRoutes;
    }

    /**
     * 通过快件编号 快件的位置信息
     *
     * @param esId
     * @return
     */
    @Override
    public List<PackageRoute> getExpressSheetRouteList(String esId) {
        //检查快件信息是否存在
        ExpressSheet expressSheet = expressSheetService.get(esId);
        if (expressSheet == null) {
            throw new ServiceException(2000, "快件不存在");
        }

        //获取包裹位置信息
        List<TransPackageContent> transPackageContentList = transPackageContentService.findByExpressId(esId);
        List<String> packageIds = new ArrayList<>();
        if (transPackageContentList != null && !transPackageContentList.isEmpty()) {
            for (TransPackageContent transPackageContent : transPackageContentList) {
                packageIds.add(transPackageContent.getPackageId());
            }
        }
        return packageRouteDao.getByPackageIds(packageIds);
    }
}
