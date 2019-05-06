package com.expressTracking.action;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.expressTracking.entity.*;
import com.expressTracking.service.*;
import com.expressTracking.utils.JsonUtils;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.google.gson.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName PackageController
 * @Decsription TODO
 * @Author liwei
 * @Date 2019/5/3 23:09
 * @Version 1.0
 */
@RestController
@RequestMapping("/package")
public class PackageController {

    @Autowired
    private PackageRecordService packageRecordService;
    @Autowired
    private PackageRouteService packageRouteService;
    @Autowired
    private TransPackageContentService tPackageContentService;
    @Autowired
    private TransPackageService transPackageService;
    @Autowired
    private UserPackageService userPackageService;
    @Autowired
    private ExpressSheetService expressSheetService;
    @Autowired
    private UserInfoService userInfoService;


    /**
     * 创建包裹信息
     *
     * @param packageId
     * @param userId
     * @return
     */
    @RequestMapping("/create/{packageId}/{userId}")
    public JSONObject createPackage(@PathVariable("packageId") String packageId, @PathVariable("userId") Integer userId) {
        JSONObject jsonObject = new JSONObject();
        if (packageId != null && userId != null) {
            TransPackage transPackage = transPackageService.get(packageId);
            if (transPackage != null) {
                jsonObject.put("message", "包裹信息已存在");
            } else if (userInfoService.get(userId) == null) {
                jsonObject.put("message", "用户信息不存在");
            } else {
                if (transPackageService.newTransPackage(packageId, userId) > 0) {
                    jsonObject.put("message", "包裹创建成功");
                    transPackage = transPackageService.get(packageId);

                    jsonObject.put("package", JSON.parse(JsonUtils.toJson(transPackage)));
                } else {
                    jsonObject.put("message", "包裹创建失败");
                }
            }
        } else {
            jsonObject.put("message", "参数错误");
        }
        return jsonObject;
    }

    /**
     * 修改包裹
     *
     * @param transPackage
     * @return
     */
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public JSONObject updatePackage(@RequestBody TransPackage transPackage) {
        JSONObject jsonObject = new JSONObject();
        if (transPackage != null) {
            if (transPackageService.update(transPackage) > 0) {
                jsonObject.put("message", "保存成功");
                jsonObject.put("package", JSON.parse(JsonUtils.toJson(transPackage)));
            } else {
                jsonObject.put("message", "保存失败");
            }
        } else {
            jsonObject.put("message", "参数错误");
        }
        return jsonObject;
    }

    /**
     * 添加快件到包裹中
     *
     * @param packageId
     * @param esId
     * @return
     */
    @RequestMapping("/move_in_es/{packageId}/{esId}")
    public JSONObject moveEsToPackage(@PathVariable("packageId") String packageId, @PathVariable("esId") String esId) {
        JSONObject jsonObject = new JSONObject();
        if (packageId != null && esId != null) {
            TransPackage transPackage = null;
            TransPackageContent transPackageContent = null;
            if ((transPackage = transPackageService.get(packageId)) == null) {
                jsonObject.put("message", "包裹信息不存在");
            } else if (expressSheetService.get(esId) == null) {
                jsonObject.put("message", "快件信息不存在");
            } else if (tPackageContentService.moveEsToPackage(packageId, esId) > 0) {
                jsonObject.put("message", "快件打包成功");
            } else {
                jsonObject.put("message", "快件打包失败");
            }
        } else {
            jsonObject.put("message", "参数错误");
        }
        return jsonObject;
    }

    /**
     * 从包裹中移出快件信息
     *
     * @param packageId
     * @param esId
     * @return
     */
    @RequestMapping("/move_out_es/{packageId}/{esId}")
    public JSONObject moveEsOutPackage(@PathVariable("packageId") String packageId, @PathVariable("esId") String esId) {
        JSONObject jsonObject = new JSONObject();
        if (packageId != null && esId != null) {
            TransPackage transPackage = null;
            TransPackageContent transPackageContent = null;
            if ((transPackage = transPackageService.get(packageId)) == null) {
                jsonObject.put("message", "包裹信息不存在");
            } else if (transPackage.getStatus() == TransPackage.PACKAGE_COLLECT) {
                jsonObject.put("message", "揽收货篮,不可以取出包裹");
            } else if (expressSheetService.get(esId) == null) {
                jsonObject.put("message", "快件信息不存在");
            } else if (tPackageContentService.moveEsOutPackage(packageId, esId) > 0) {
                jsonObject.put("message", "快件移出成功");
            } else {
                jsonObject.put("message", "快件移出成功失败");
            }
        } else {
            jsonObject.put("message", "参数错误");
        }
        return jsonObject;
    }

    /**
     * 拆包
     *
     * @param pacakgeId
     * @param userId
     * @return
     */
    @RequestMapping("/unpack/{packageId}/{userId}")
    public JSONObject unpack(@PathVariable("packageId") String pacakgeId, @PathVariable("userId") Integer userId) {
        JSONObject jsonObject = new JSONObject();
        if (pacakgeId != null && userId != null) {
            TransPackage transPackage = transPackageService.get(pacakgeId);
            if (transPackage == null) {
                jsonObject.put("message", "包裹信息不存在");
            } else if (userInfoService.get(userId) == null) {
                jsonObject.put("message", "用户信息不存在");
            } else if (transPackage.getStatus() == TransPackage.PACKAGE_NEW) {
                jsonObject.put("message", "包裹处于新建状态，不可以拆包");
            } else if (transPackageService.unPackTransPckage(pacakgeId, userId) > 0) {
                jsonObject.put("message", "拆包成功");
                transPackage = transPackageService.get(pacakgeId);
                jsonObject.put("package", JSON.parse(JsonUtils.toJson(transPackage)));
            }
        } else {
            jsonObject.put("message", "参数错误");
        }
        return jsonObject;
    }

    /**
     * @param packageId
     * @return
     */
    @RequestMapping("/get_package/{packageId}")
    public JSONObject getPackage(@PathVariable("packageId") String packageId) {
        JSONObject jsonObject = new JSONObject();
        if (packageId != null) {
            TransPackage transPackage = transPackageService.get(packageId);
            if (transPackage != null) {
                jsonObject.put("message", "查询成功");
                jsonObject.put("package", JSON.parse(JsonUtils.toJson(transPackage)));
            } else {
                jsonObject.put("message", "查询失败");
            }
        } else {
            jsonObject.put("message", "参数错误");
        }
        return jsonObject;
    }

    /**
     * 得到包裹集合
     *
     * @param property     属性 ID or SourceNode or TargetNode or Status
     * @param restrictions 限定条件 eq or like
     * @param value        属性值
     * @return
     */
    @RequestMapping(value = "/get_package_list/{property}/{restrictions}/{value}", method = RequestMethod.GET)
    public JSONObject getPackageList(@PathVariable("property") String property,
                                     @PathVariable("restrictions") String restrictions,
                                     @PathVariable("value") String value) {
        JSONObject jsonObject = new JSONObject();
        if (property != null && restrictions != null && value != null) {
            List<TransPackage> transPackageList = new ArrayList<>();
            switch (restrictions) {
                case "like": {
                    transPackageList.addAll(transPackageService.findLike(property, value));
                    break;
                }
                case "eq":
                default: {
                    transPackageList.addAll(transPackageService.findBy(property, value));
                    break;
                }
            }
            jsonObject.put("message", "查询成功");
            jsonObject.put("packageList", JSON.parse(JsonUtils.toJson(transPackageList)));
        } else {
            jsonObject.put("message", "参数错误");
        }
        return jsonObject;
    }

//    public JSONObject

}
