package com.expressTracking.action;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.expressTracking.entity.ExpressSheet;
import com.expressTracking.entity.ResponseCode;
import com.expressTracking.entity.UserInfo;
import com.expressTracking.service.ExpressSheetService;
import com.expressTracking.service.UserInfoService;
import com.expressTracking.utils.JsonUtils;
import com.google.gson.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @ClassName ExpressSheetController
 * @Decsription TODO
 * @Author liwei
 * @Date 2019/5/3 16:58
 * @Version 1.0
 */
@RestController
@RequestMapping("/es")
public class ExpressSheetController {

    @Autowired
    private ExpressSheetService esService;
    @Autowired
    private UserInfoService userInfoService;

    /**
     * 创建快件信息
     *
     * @param esId     快件编号
     * @param accepter 揽收人ID
     * @return
     */
//    @RequestMapping("/create/{esId}/{accepter}")
//    public JSONObject createEs(@PathVariable("esId") String esId, @PathVariable("accepter") Integer accepter) throws Exception {
//        JSONObject jsonObject = new JSONObject();
//        if (esId != null && accepter != null) {
//            System.out.println(esId + "=======" + accepter);
//            ExpressSheet expressSheet = esService.get(esId);
//            UserInfo userInfo = userInfoService.get(accepter);
//            if (expressSheet != null) {
//                jsonObject.put("message", "快件信息已存在");
//                jsonObject.put("es", JSON.parse(JsonUtils.toJson(expressSheet)));
//            } else if (userInfo == null) {
//                jsonObject.put("message", "用户信息不存在");
//            } else {
//                if (esService.create(esId, accepter) > 0) {
//                    expressSheet = esService.get(esId);
//                    if (expressSheet != null) {
//                        jsonObject.put("message", "快件创建成功");
//                        jsonObject.put("es", JSON.parse(JsonUtils.toJson(expressSheet)));
//                    }
//                } else {
//                    jsonObject.put("message", "快件创建失败");
//                }
//                System.out.println(expressSheet);
//            }
//        }
//        return jsonObject;
//    }

    /**
     * 创建快件信息
     * 揽收
     *
     * @param es
     * @return
     */
    @RequestMapping(value = "/create/{userId}", method = RequestMethod.POST)
    public JSONObject create(@RequestBody ExpressSheet es, @PathVariable("userId") Integer userId) {
        System.out.println(es);
        JSONObject jsonObject = new JSONObject();
        ResponseCode code = new ResponseCode();
        code.setCode(ResponseCode.Result.FAIL);
        //检查快递
        if (es != null && es.getId() != null && userId != null) {
            switch (esService.create(es, userId)){
                case 1:{
                    code.setMessage("快件信息已存在");
                    break;
                }
                case 2:{
                    code.setMessage("用户不存在");
                    break;
                }
                case 3:{
                    code.setCode(ResponseCode.Result.SUCESS);
                    ExpressSheet expressSheet = esService.get(es.getId());
                    jsonObject.put("es",expressSheet);
                }
                default:{
                    code.setMessage("揽收失败");
                    break;
                }
            }
        } else {
            code.setCode(ResponseCode.Result.ERROR);
            code.setMessage("参数错误");
        }
        jsonObject.put("code", JSON.parse(JsonUtils.toJson(code)));
        return jsonObject;
    }

    @RequestMapping("/delete/{esId}")
    public JSONObject delete(@PathVariable("esId") String esId) {
        JSONObject jsonObject = new JSONObject();
        ResponseCode code = new ResponseCode();
        if (esId != null) {
            ExpressSheet expressSheet = esService.get(esId);
            if (expressSheet == null) {
                code.setCode(ResponseCode.Result.FAIL);
                code.setMessage("快件" + esId + "不存在");
            } else {
                if (esService.delete(esId) > 0) {
                    code.setCode(ResponseCode.Result.SUCESS);
                } else {
                    code.setCode(ResponseCode.Result.FAIL);
                }
            }
        } else {
            code.setCode(ResponseCode.Result.ERROR);
            code.setMessage("参数错误");
        }
        jsonObject.put("code", JSON.parse(JsonUtils.toJson(code)));
        return jsonObject;
    }

    /**
     * 修改快件信息
     *
     * @param expressSheet
     * @return
     */
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public JSONObject update(@RequestBody ExpressSheet expressSheet) {
        JSONObject jsonObject = new JSONObject();
        ResponseCode code = new ResponseCode();
        if (expressSheet != null) {
            if (esService.update(expressSheet) > 0) {
                code.setCode(ResponseCode.Result.SUCESS);
                jsonObject.put("es", JSON.parse(JsonUtils.toJson(expressSheet)));
            } else {
                code.setCode(ResponseCode.Result.FAIL);
            }
        } else {
            code.setCode(ResponseCode.Result.ERROR);
            code.setMessage("参数错误");
        }
        jsonObject.put("code", JSON.parse(JsonUtils.toJson(code)));
        return jsonObject;
    }

    /**
     * 修改快件状态
     *
     * @param esId
     * @param status
     * @return
     */
    @RequestMapping("/update/{esId}/{status}")
    public JSONObject update(@PathVariable("esId") String esId, @PathVariable("status") Integer status) {
        JSONObject jsonObject = new JSONObject();
        ResponseCode code = new ResponseCode();
        if (esId != null && status != null) {
            ExpressSheet expressSheet = esService.get(esId);
            if (expressSheet == null) {
                code.setCode(ResponseCode.Result.FAIL);
                code.setMessage("快件" + esId + "不存在");
            } else if (status < ExpressSheet.STATUS.STATUS_CREATED || status > ExpressSheet.STATUS.STATUS_DELIVERIED) {
                code.setCode(ResponseCode.Result.FAIL);
                code.setMessage("快件状态不合法");
            } else {
                expressSheet.setStatus(status);
                if (esService.update(expressSheet) > 0) {
                    code.setCode(ResponseCode.Result.SUCESS);
                    code.setMessage("快件状态修改成功");
                    jsonObject.put("es", JSON.parse(JsonUtils.toJson(expressSheet)));
                } else {
                    code.setCode(ResponseCode.Result.FAIL);
                }
            }
        } else {
            code.setCode(ResponseCode.Result.ERROR);
            code.setMessage("参数错误");
        }
        jsonObject.put("code", JSON.parse(JsonUtils.toJson(code)));
        return jsonObject;
    }

    /**
     * 交付快件
     *
     * @param esId
     * @param deliver
     * @return
     */
    @RequestMapping("/deliver/{esId}/{deliver}")
    public JSONObject deliverEs(@PathVariable("esId") String esId, @PathVariable("deliver") Integer deliver) {
        JSONObject jsonObject = new JSONObject();
        ResponseCode code = new ResponseCode();
        if (esId != null && deliver != null) {
            ExpressSheet expressSheet = esService.get(esId);
            UserInfo deliverInfo = userInfoService.get(deliver);
            if (expressSheet == null) {
                code.setCode(ResponseCode.Result.FAIL);
                code.setMessage("快件" + esId + "不存在");
            } else if (deliverInfo == null) {
                code.setCode(ResponseCode.Result.FAIL);
                code.setMessage("用户" + deliver + "不存在");
            } else {
                expressSheet.setStatus(ExpressSheet.STATUS.STATUS_DELIVERIED);
                expressSheet.setDeliver(deliver + "");
            }
        } else {
            code.setCode(ResponseCode.Result.ERROR);
            code.setMessage("参数错误");
        }
        jsonObject.put("code", JSON.parse(JsonUtils.toJson(code)));
        return jsonObject;
    }

    /**
     * 通过快件编号查找快件信息
     *
     * @param esId
     * @return
     */
    @RequestMapping("/get/{esId}")
    public JSONObject getEs(@PathVariable("esId") String esId) {
        JSONObject jsonObject = new JSONObject();

        ResponseCode code = new ResponseCode();
        if (esId != null) {
            ExpressSheet expressSheet = esService.get(esId);
            if (expressSheet != null) {
                code.setCode(ResponseCode.Result.SUCESS);
                jsonObject.put("es", JSON.parse(JsonUtils.toJson(expressSheet)));
            } else {
                code.setCode(ResponseCode.Result.FAIL);
                code.setMessage("快件" + esId + "不存在");
            }
        } else {
            code.setCode(ResponseCode.Result.ERROR);
            code.setMessage("参数错误");
        }
        jsonObject.put("code", JSON.parse(JsonUtils.toJson(code)));
        return jsonObject;
    }

    /**
     * 多条件查询快件列表
     * 通过 id，senderId receverId accepter deliver status查询
     *
     * @param expressSheet
     * @return
     */
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public JSONObject getEsList(@RequestBody ExpressSheet expressSheet) {
        JSONObject jsonObject = new JSONObject();
        ResponseCode code = new ResponseCode();
        if (expressSheet != null) {
            List<ExpressSheet> expressSheetList = esService.getByParameters(expressSheet);
            if (expressSheetList != null) {
                code.setCode(ResponseCode.Result.SUCESS);
                jsonObject.put("esList", JSON.parse(JsonUtils.toJson(expressSheetList)));
            } else {
                code.setCode(ResponseCode.Result.FAIL);
                code.setMessage("查询失败");
            }
        } else {
            code.setCode(ResponseCode.Result.ERROR);
            code.setMessage("参数错误");
        }
        jsonObject.put("code", JSON.parse(JsonUtils.toJson(code)));
        return jsonObject;
    }

    /**
     * 获取由userId创建的状态为status的快件列表信息
     *
     * @param userId
     * @param status
     * @return
     */
    @RequestMapping(value = "/list",method = RequestMethod.GET)
    public JSONObject getEsList(Integer userId, Integer status) {
        JSONObject jsonObject = new JSONObject();
        ResponseCode code = new ResponseCode();
        if (userId != null && status != null) {
            UserInfo userInfo = userInfoService.get(userId);
            if(userInfo != null){
                List<ExpressSheet> expressSheets = esService.getByAccpterAndStatus(userId + "",status);
                code.setCode(ResponseCode.Result.SUCESS);
                jsonObject.put("expressSheetList",JSON.parse(JsonUtils.toJson(expressSheets)));
            }else{
                code.setCode(ResponseCode.Result.FAIL);
                code.setMessage("用户信息不存在");
            }
        } else {
            code.setCode(ResponseCode.Result.ERROR);
            code.setMessage("参数错误");
        }
        jsonObject.put("code", JSON.parse(JsonUtils.toJson(code)));
        return jsonObject;
    }
    /**
     * 查询包裹中的快件信息
     * @param packageId
     */
    @RequestMapping("/getEsFormPakcage/{packageId}")
    public JSONObject getEsListFromPackage(@PathVariable("packageId") String packageId){
        JSONObject jsonObject = new JSONObject();
        ResponseCode code = new ResponseCode();
        code.setCode(ResponseCode.Result.FAIL);
        if (packageId != null){
            List<ExpressSheet> expressSheets = esService.getListInPackage(packageId);
            code.setCode(ResponseCode.Result.SUCESS);
            jsonObject.put("esList",JSON.parse(JsonUtils.toJson(expressSheets)));
        }else{
            code.setCode(ResponseCode.Result.ERROR);
            code.setMessage("参数错误");
        }
        jsonObject.put("code", code);
        return jsonObject;
    }

}
