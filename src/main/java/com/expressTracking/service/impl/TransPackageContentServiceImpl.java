package com.expressTracking.service.impl;

import com.expressTracking.dao.TransPackageContentDao;
import com.expressTracking.entity.ExpressSheet;
import com.expressTracking.entity.TransPackage;
import com.expressTracking.entity.TransPackageContent;
import com.expressTracking.service.ExpressSheetService;
import com.expressTracking.service.TransPackageContentService;
import com.expressTracking.service.TransPackageService;
import jdk.net.SocketFlow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author muwei
 * @date 2019/4/8
 */
@Service
@Transactional(isolation = Isolation.READ_COMMITTED,
        propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
public class TransPackageContentServiceImpl implements TransPackageContentService {

    @Autowired
    private TransPackageContentDao transPackageContentDao;
    @Autowired
    private ExpressSheetService expressSheetService;
    @Autowired
    private TransPackageService transPackageService;

    @Override
    public void save(TransPackageContent transPackageContent) {
        transPackageContentDao.insert(transPackageContent);
    }

    @Override
    public int update(TransPackageContent transPackageContent) {
        return transPackageContentDao.update(transPackageContent);
    }

    @Override
    public int updateStatusByPackageId(String packageId, Integer status) {
        return 0;
    }

    /**
     * 1 包裹存在 2 快件不存在 3 快件状态错误 4 快件已在包裹中 5 添加失败 6 添加成功
     *
     * @param packageId 包裹编号
     * @param esId
     * @return
     */
    @Override
    public int moveEsToPackage(String packageId, String esId) {
        TransPackage transPackage = transPackageService.get(packageId);
        //包裹不存在
        if (transPackage == null) {
            return 1;
        }
        ExpressSheet expressSheet = expressSheetService.get(esId);
        //快件不存在
        if (expressSheet == null) {
            return 2;
        }
        //快件处于揽收和分拣状态时才可以移入包裹中
        switch (expressSheet.getStatus()) {
            case ExpressSheet.STATUS.STATUS_CREATED: {
                expressSheetService.updateEsStatus(esId, ExpressSheet.STATUS.STATUS_RECEIVED);
                break;
            }
            case ExpressSheet.STATUS.STATUS_RECEIVED:
            case ExpressSheet.STATUS.STATUS_PARTATION: {
                break;
            }
            default: {
                //快件状态错误
                return 3;
            }
        }
        //检查快件是否在包裹中
        if (transPackage.getContent() != null) {
            for (TransPackageContent transPackageContent : transPackage.getContent()) {
                //快件已在包裹中
                if (transPackageContent.getExpressId().equals(esId)) {
                    if (transPackageContent.getStatus() == TransPackageContent.STATUS.STATUS_ACTIVE) {
                        //包裹已在快件中
                        return 4;
                    }
                }
            }
        }

        expressSheet.setStatus(ExpressSheet.STATUS.STATUS_PACKAGED);//将快件状态设为已打包状态

        expressSheetService.update(expressSheet);

        if (transPackage.getStatus() == TransPackage.PACKAGE_NEW) {
            transPackage.setStatus(TransPackage.PACKAGE_PACK);
            transPackageService.update(transPackage);
        }

        return addTransPackageContent(packageId, esId) > 0 ? 6 : 5;
    }

    /**
     * 添加TransPackageContent
     *
     * @param packageId
     * @param esId
     * @return
     */
    public int addTransPackageContent(String packageId, String esId) {
        TransPackageContent transPackageContent = new TransPackageContent();
        transPackageContent.setExpressId(esId);
        transPackageContent.setPackageId(packageId);
        transPackageContent.setStatus(TransPackageContent.STATUS.STATUS_ACTIVE);
        return transPackageContentDao.insert(transPackageContent);
    }

    /**
     * 1 包裹不存在 2 快件不在包裹中 3 移出失败 4 移出成功
     * 步骤
     * 1 判断包裹是否存在
     * 2 包裹不存在 返回 1
     * 3 包裹存在 判断快件是否在包裹中
     * 4 如果快件不在包裹中  返回 2
     * 5 快件在包裹中 移出快件并将快件状态置位分拣状态
     * 6 成功返回 4 失败返回 3
     *
     * @param packageId
     * @param esId
     * @return
     */
    @Override
    public int moveEsOutPackage(String packageId, String esId) {
        TransPackage transPackage = transPackageService.get(packageId);
        //包裹不存在
        if (transPackage == null) {
            return 1;
        }

        TransPackageContent transPackageContent = containExpress(packageId, esId);
        if (transPackageContent == null || transPackageContent.getStatus() == TransPackageContent.STATUS.STATUS_OUTOF_PACKAGE) {
            //快件不在包裹中
            return 2;
        } else {
            transPackageContent.setStatus(TransPackageContent.STATUS.STATUS_OUTOF_PACKAGE);
            //移出快件并将快件状态置位分拣状态
            return update(transPackageContent) * expressSheetService.updateEsStatus(esId, ExpressSheet.STATUS.STATUS_PARTATION) > 0 ? 4 : 3;
        }


//        TransPackageContent transPackageContent = containExpress(packageId, esId);
//        if (transPackageContent != null) {
//            ExpressSheet expressSheet = expressSheetService.get(transPackageContent.getExpressId());
//            expressSheet.setStatus(ExpressSheet.STATUS.STATUS_PARTATION);
//            if (transPackageContent.getStatus() == TransPackageContent.STATUS.STATUS_ACTIVE) {
//                transPackageContent.setStatus(TransPackageContent.STATUS.STATUS_OUTOF_PACKAGE);
//                return transPackageContentDao.update(transPackageContent) * expressSheetService.update(expressSheet);
//            } else {
//                return expressSheetService.update(expressSheet);
//            }
//        }
//        return 0;
    }

    @Override
    public TransPackageContent findByExpressIdAndStatus(String expressId, int status) {
        List<TransPackageContent> transPackageContents = transPackageContentDao.findByExpressIdAndStatus(expressId, status);
        if (transPackageContents != null && !transPackageContents.isEmpty()) {
            return transPackageContents.get(0);
        }
        return null;
    }

    @Override
    public List<TransPackageContent> findByPackageIdAndStatus(String packageId, int status) {
        return transPackageContentDao.findByPackageIdAndStatus(packageId, status);
    }

    @Override
    public List<TransPackageContent> findByExpressId(String expressId) {
        return transPackageContentDao.findByExpressIdAndStatus(expressId, null);
    }

//    @Override
//    public List<TransPackageContent> findByPackageId(String packageId) {
//        return null;
//    }

    @Override
    public TransPackageContent containExpress(String packageId, String esId) {
        return transPackageContentDao.findByPackageIdAndEsId(packageId, esId);
    }

    @Override
    public List<String> getPackageId(String expressId) {
        return transPackageContentDao.getPackageId(expressId);
    }
}
