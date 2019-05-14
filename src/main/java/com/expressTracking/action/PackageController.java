package com.expressTracking.action;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.expressTracking.entity.*;
import com.expressTracking.service.*;
import com.expressTracking.utils.JsonUtils;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.google.gson.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.xml.ws.Response;
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
    @Qualifier("transPackageService")
    private TransPackageService transPackageService;

    @Autowired
    private UserPackageService userPackageService;

    @Autowired
    private ExpressSheetService expressSheetService;

    @Autowired
    private UserInfoService userInfoService;


    /**
     * 新建包裹信息
     *
     * @param transPackage
     * @param userId
     * @return
     */
    @RequestMapping(value = "/save/{userId}", method = RequestMethod.POST)
    public JSONObject newTransPackage(@RequestBody TransPackage transPackage, @PathVariable("userId") Integer userId) {
        JSONObject jsonObject = new JSONObject();
        ResponseCode code = new ResponseCode();
        code.setCode(ResponseCode.Result.FAIL);
        System.out.println(transPackage);
        if (transPackage != null && userId != null && transPackage.getId() != null) {
            if (transPackageService.get(transPackage.getId()) == null) {
                if (transPackageService.newTransPackage(transPackage, userId) > 0) {
                    code.setCode(ResponseCode.Result.SUCESS);
                    jsonObject.put("package", JSON.parse(JsonUtils.toJson(transPackageService.get(transPackage.getId()))));
                } else {
                    code.setMessage("创建包裹失败");
                }
            } else {
                code.setMessage("包裹信息已存在");
            }
        } else {
            code.setCode(ResponseCode.Result.ERROR);
            code.setMessage("参数错误");
        }
        jsonObject.put("code", code);
        return jsonObject;
    }


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
        ResponseCode code = new ResponseCode();
        if (packageId != null && userId != null) {
            TransPackage transPackage = transPackageService.get(packageId);
            if (transPackage != null) {
                code.setCode(ResponseCode.Result.FAIL);
                code.setMessage("包裹信息已存在");
            } else if (userInfoService.get(userId) == null) {
                code.setCode(ResponseCode.Result.FAIL);
                code.setMessage("用户信息不存在");
            } else {
                if (transPackageService.newTransPackage(packageId, userId) > 0) {
                    code.setCode(ResponseCode.Result.SUCESS);
                    transPackage = transPackageService.get(packageId);
                    jsonObject.put("package", JSON.parse(JsonUtils.toJson(transPackage)));
                } else {
                    code.setCode(ResponseCode.Result.FAIL);
                    code.setMessage("包裹创建失败");
                }
            }
        } else {
            code.setCode(ResponseCode.Result.ERROR);
            code.setMessage("参数错误");
        }

        jsonObject.put("code", code);
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
        ResponseCode code = new ResponseCode();
        if (transPackage != null) {
            if (transPackageService.update(transPackage) > 0) {
                code.setCode(ResponseCode.Result.SUCESS);
                jsonObject.put("package", JSON.parse(JsonUtils.toJson(transPackage)));
            } else {
                code.setCode(ResponseCode.Result.FAIL);
                code.setMessage("保存失败");
            }
        } else {
            code.setCode(ResponseCode.Result.ERROR);
            code.setMessage("参数错误");
        }

        jsonObject.put("code", code);
        return jsonObject;
    }

    /**
     * 添加快件到包裹中
     *
     * @param packageId
     * @param esId
     * @return
     */
    @RequestMapping("/move_es_in/{packageId}/{esId}")
    public JSONObject moveEsToPackage(@PathVariable("packageId") String packageId, @PathVariable("esId") String esId) {
        JSONObject jsonObject = new JSONObject();
        ResponseCode code = new ResponseCode();
        code.setCode(ResponseCode.Result.FAIL);
        if (packageId != null && esId != null) {
            switch (tPackageContentService.moveEsToPackage(packageId, esId)) {
                case 1:
                    code.setMessage("包裹不存在");
                    break;
                case 2:
                    code.setMessage("快件不存在");
                    break;
                case 3:
                    code.setMessage("快件状态错误");
                    break;
                case 4:
                    code.setMessage("快件已在包裹中");
                    break;
                case 5:
                    code.setMessage("添加失败");
                    break;
                case 6:
                    code.setCode(ResponseCode.Result.SUCESS);
                    break;
                default:
                    code.setMessage("参数错误");
                    break;
            }
        } else {
            code.setCode(ResponseCode.Result.ERROR);
            code.setMessage("参数错误");
        }
        jsonObject.put("code", code);
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
        ResponseCode code = new ResponseCode();
        code.setCode(ResponseCode.Result.FAIL);
        if (packageId != null && esId != null) {
            switch (tPackageContentService.moveEsOutPackage(packageId,esId)){
                case 1 :{
                    code.setMessage("包裹不存在");
                    break;
                }
                case 2:{
                    code.setMessage("快件不在包裹中");
                    break;
                }
//                case 3:{
//                    code.setMessage("移出快件失败");
//                    break;
//                }
                case 4:{
                    //移出成功
                    code.setCode(ResponseCode.Result.SUCESS);
                    break;
                }
                default:{
                    code.setMessage("移出快件失败");
                }
            }

//            TransPackage transPackage = null;
//            TransPackageContent transPackageContent = null;
//            if ((transPackage = transPackageService.get(packageId)) == null) {
//                code.setMessage("包裹信息不存在");
//            } else if (transPackage.getStatus() == TransPackage.PACKAGE_COLLECT) {
//                code.setMessage("揽收货篮,不可以取出包裹");
//            } else if (expressSheetService.get(esId) == null) {
//                code.setMessage("快件信息不存在");
//                jsonObject.put("message", "快件信息不存在");
//            } else if (tPackageContentService.moveEsOutPackage(packageId, esId) > 0) {
//                code.setCode(ResponseCode.Result.SUCESS);
//                code.setMessage("快件移出成功");
//            } else {
//                code.setMessage("快件移出失败");
//            }
        } else {
            code.setCode(ResponseCode.Result.ERROR);
            code.setMessage("参数错误");
        }
        jsonObject.put("code", code);
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
        ResponseCode code = new ResponseCode();
        code.setCode(ResponseCode.Result.FAIL);
        if (pacakgeId != null && userId != null) {
            switch (transPackageService.unPackTransPckage(pacakgeId,userId)){
                case 1:{
                    code.setMessage("包裹信息不存在");
                    break;
                }
                case 2:{
                    code.setMessage("用户信息不存在");
                    break;
                }
                case 3:{
                    code.setCode(ResponseCode.Result.SUCESS);
                    TransPackage transPackage = transPackageService.get(pacakgeId);
                    jsonObject.put("package", JSON.parse(JsonUtils.toJson(transPackage)));
                    break;
                }
                default:{
                    code.setMessage("拆包失败");
                    break;
                }
            }

//            TransPackage transPackage = transPackageService.get(pacakgeId);
//            if (transPackage == null) {
//                code.setMessage("包裹信息不存在");
//            } else if (userInfoService.get(userId) == null) {
//                code.setMessage("用户信息不存在");
//            } else if (transPackage.getStatus() == TransPackage.PACKAGE_NEW) {
//                code.setMessage("包裹处于新建状态，不可以拆包");
//            } else if (transPackageService.unPackTransPckage(pacakgeId, userId) > 0) {
//                code.setCode(ResponseCode.Result.SUCESS);
//                transPackage = transPackageService.get(pacakgeId);
//                jsonObject.put("package", JSON.parse(JsonUtils.toJson(transPackage)));
//            }
        } else {
            code.setCode(ResponseCode.Result.ERROR);
            code.setMessage("参数错误");
        }
        jsonObject.put("code", code);
        return jsonObject;
    }

    /**
     * @param packageId
     * @return
     */
    @RequestMapping("/get_package/{packageId}")
    public JSONObject getPackage(@PathVariable("packageId") String packageId) {
        JSONObject jsonObject = new JSONObject();
        ResponseCode code = new ResponseCode();
        code.setCode(ResponseCode.Result.FAIL);
        if (packageId != null) {
            TransPackage transPackage = transPackageService.get(packageId);
            if (transPackage != null) {
                code.setCode(ResponseCode.Result.SUCESS);
                jsonObject.put("package", JSON.parse(JsonUtils.toJson(transPackage)));
            } else {
                code.setMessage("查询失败");
            }
        } else {
            code.setCode(ResponseCode.Result.ERROR);
            code.setMessage("参数错误");
        }
        jsonObject.put("code", code);
        return jsonObject;
    }

    /**
     * 获取由 userID 执行 operation 且状态为status的包裹信息
     * @param userId
     * @param operation
     * @param status
     * @return
     */
    @RequestMapping("/getPackageList/{userId}/{operation}/{status}")
    public JSONObject getPackageList(@PathVariable("userId") Integer userId,@PathVariable("operation") Integer operation,@PathVariable("status") Integer status){
        JSONObject jsonObject = new JSONObject();
        ResponseCode code = new ResponseCode();
        if(userId != null && operation != null && status != null){
            code.setCode(ResponseCode.Result.SUCESS);
            jsonObject.put("packageList",transPackageService.getByUserId(userId,operation,status));
        }else{
            code.setCode(ResponseCode.Result.ERROR);
            code.setMessage("参数错误");
        }
        jsonObject.put("code", code);
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
        ResponseCode code = new ResponseCode();
        code.setCode(ResponseCode.Result.FAIL);
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
            code.setCode(ResponseCode.Result.SUCESS);
            jsonObject.put("packageList", JSON.parse(JsonUtils.toJson(transPackageList)));
        } else {
            code.setCode(ResponseCode.Result.ERROR);
            code.setMessage("参数错误");
        }
        jsonObject.put("code", code);
        return jsonObject;
    }

    //转运



}
