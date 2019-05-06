package com.expressTracking.action;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.expressTracking.entity.ExpressSheet;
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
    @RequestMapping("/create/{esId}/{accepter}")
    public JSONObject createEs(@PathVariable("esId") String esId, @PathVariable("accepter") Integer accepter) throws Exception {
        JSONObject jsonObject = new JSONObject();
        if (esId != null && accepter != null) {
            System.out.println(esId + "=======" + accepter);
            ExpressSheet expressSheet = esService.get(esId);
            UserInfo userInfo = userInfoService.get(accepter);
            if (expressSheet != null) {
                jsonObject.put("message", "快件信息已存在");
                jsonObject.put("es", JSON.parse(JsonUtils.toJson(expressSheet)));
            } else if (userInfo == null) {
                jsonObject.put("message", "用户信息不存在");
            } else {
                if (esService.create(esId, accepter) > 0) {
                    expressSheet = esService.get(esId);
                    if (expressSheet != null) {
                        jsonObject.put("message", "快件创建成功");
                        jsonObject.put("es", JSON.parse(JsonUtils.toJson(expressSheet)));
                    }
                } else {
                    jsonObject.put("message", "快件创建失败");
                }
                System.out.println(expressSheet);
            }
        }
        return jsonObject;
    }

    @RequestMapping("/delete/{esId}")
    public JSONObject delete(@PathVariable("esId") String esId) {
        JSONObject jsonObject = new JSONObject();
        if (esId != null) {
            ExpressSheet expressSheet = esService.get(esId);
            if (expressSheet == null) {
                jsonObject.put("message", "快件" + esId + "不存在");
            } else {
                if (esService.delete(esId) > 0) {
                    jsonObject.put("message", "删除快件" + esId + "成功");
                } else {
                    jsonObject.put("message", "删除快件" + esId + "失败");
                }
            }
        } else {
            jsonObject.put("message", "参数错误");
        }
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
        if (expressSheet != null && esService.update(expressSheet) > 0) {
            jsonObject.put("message", "修改成功！");
            jsonObject.put("es",JSON.parse(JsonUtils.toJson(expressSheet)));
        } else {
            jsonObject.put("message", "修改失败！");
        }
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
        if (esId != null && status != null) {
            ExpressSheet expressSheet = esService.get(esId);
            if (expressSheet == null) {
                jsonObject.put("message", "快件" + esId + "不存在");
            } else if (status < ExpressSheet.STATUS.STATUS_CREATED || status > ExpressSheet.STATUS.STATUS_DELIVERIED) {
                jsonObject.put("message", "快件状态不合法");
            } else {
                expressSheet.setStatus(status);
                if (esService.update(expressSheet) > 0) {
                    jsonObject.put("message", "快件状态修改成功");
                    jsonObject.put("es", JSON.parse(JsonUtils.toJson(expressSheet)));
                }
            }
        } else {
            jsonObject.put("message", "参数错误");
        }
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
        if (esId != null && deliver != null) {
            ExpressSheet expressSheet = esService.get(esId);
            UserInfo deliverInfo = userInfoService.get(deliver);
            if (expressSheet == null) {
                jsonObject.put("message", "快件" + esId + "不存在");
            } else if (deliverInfo == null) {
                jsonObject.put("message", "用户" + deliver + "不存在");
            } else {
                expressSheet.setStatus(ExpressSheet.STATUS.STATUS_DELIVERIED);
                expressSheet.setDeliver(deliver + "");
            }
        } else {
            jsonObject.put("message", "参数错误");
        }
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
        if (esId != null) {
            ExpressSheet expressSheet = esService.get(esId);
            if (expressSheet != null) {
                jsonObject.put("message", "查询成功");
                jsonObject.put("es", JSON.parse(JsonUtils.toJson(expressSheet)));
            } else {
                jsonObject.put("message", "快件" + esId + "不存在");
            }
        } else {
            jsonObject.put("message", "参数错误");
        }
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
        if (expressSheet != null) {
            List<ExpressSheet> expressSheetList = esService.getByParameters(expressSheet);
            if (expressSheetList != null) {
                jsonObject.put("message", "查询成功");
                jsonObject.put("esList", JSON.parse(JsonUtils.toJson(expressSheetList)));
            } else {
                jsonObject.put("message", "查询失败");
            }
        } else {
            jsonObject.put("message", "参数错误");
        }
        return jsonObject;
    }


}
