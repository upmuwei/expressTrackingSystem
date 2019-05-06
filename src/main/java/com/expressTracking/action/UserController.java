package com.expressTracking.action;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.expressTracking.entity.UserInfo;
import com.expressTracking.service.UserInfoService;
import com.expressTracking.utils.JsonUtils;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @ClassName UserController
 * @Decsription TODO
 * @Author liwei
 * @Date 2019/5/3 14:35
 * @Version 1.0
 */
@RestController("userControllerV1")
@RequestMapping("/user")
public class UserController {
    @Autowired
    public UserInfoService userInfoService;

    /**
     * 用户注册
     *
     * @param userInfo
     * @return
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public JSONObject register(@RequestBody UserInfo userInfo) throws Exception {
        JSONObject jsonObject = new JSONObject();
        if (userInfo != null && userInfo.getTelCode() != null) {
            if (userInfoService.getUserByTelCode(userInfo.getTelCode()) != null) {
                jsonObject.put("message", "该用户已注册");
            } else if (userInfoService.save(userInfo) > 0) {
                jsonObject.put("message", "注册成功");
                userInfo = userInfoService.getUserByTelCode(userInfo.getTelCode());
                jsonObject.put("user", JSON.parse(JsonUtils.toJson(userInfo)));
            } else {
                jsonObject.put("message", "注册失败");
            }
        } else {
            jsonObject.put("message", "参数错误");
        }
        return jsonObject;
    }

    /**
     * 用户登录
     *
     * @param map
     * @param session
     * @return
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public JSONObject doLogin(@RequestBody Map<String, String> map, HttpSession session) {
        String phone = map.get("phone");
        String password = map.get("password");
        UserInfo userInfo = userInfoService.checkLogin(phone, password);
        JSONObject jsonObject = new JSONObject();
        System.out.println(jsonObject.toString());
        if (userInfo != null) {
            jsonObject.put("message", "登录成功");
            jsonObject.put("user", JSON.parse(JsonUtils.toJson(userInfo)));
            String sessionId = userInfo.getTelCode().hashCode() + "" + System.currentTimeMillis();
            jsonObject.put("sessionId", sessionId);
            session.setAttribute(userInfo.getuId() + "", session);
        } else {
            jsonObject.put("message", "登录失败");
        }
        return jsonObject;
    }

    /**
     * 通过用户ID获取用户
     *
     * @param userId
     * @return
     */
    @RequestMapping(value = "/get/{userId}", method = RequestMethod.GET)
    public JSONObject getUserInfo(@PathVariable("userId") Integer userId) {
        UserInfo userInfo = userInfoService.get(userId);
        JSONObject jsonObject = new JSONObject();
        if (userInfo != null) {
            jsonObject.put("user", JSON.parse(JsonUtils.toJson(userInfo)));
        } else {
            jsonObject.put("message", "没有该用户信息");
        }
        return jsonObject;
    }

    /**
     * 用户退出登录
     *
     * @param userId
     * @param session
     * @return
     */
    @RequestMapping(value = "/logout/{userId}", method = RequestMethod.GET)
    public JSONObject logout(@PathVariable("userId") Integer userId, HttpSession session) {
        UserInfo userInfo = userInfoService.get(userId);
        JSONObject jsonObject = new JSONObject();
        if (userInfo != null) {
            session.removeAttribute(userId + "");
            jsonObject.put("message", "用户" + userInfo.getTelCode() + "退出登录");
        } else {
            jsonObject.put("message", "用户" + userId + "不存在");
        }
        return jsonObject;
    }

    /**
     * 修改用户信息
     *
     * @param userInfo
     * @return
     */
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public JSONObject update(@RequestBody UserInfo userInfo) {
        JSONObject jsonObject = new JSONObject();
        if (userInfoService.update(userInfo) > 0) {
            jsonObject.put("message", "用户信息修改成功");
        } else {
            jsonObject.put("messae", "用户信息修改失败");
        }
        return jsonObject;
    }

    /**
     * 查询员工信息
     *
     * @param property     属性 UID or Name or TelCode or DptID or ReceivePackageID or DelivePackageID or TransPackageID
     * @param restrictions 限制条件 eq or like
     * @param value        属性值
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/list/{property}/{restrictions}/{value}", method = RequestMethod.GET)
    public JSONObject getUserList(@PathVariable("property") String property,
                                  @PathVariable("restrictions") String restrictions,
                                  @PathVariable("value") String value) throws Exception {
        List<UserInfo> userInfoList = new ArrayList<>();
        switch (restrictions.toLowerCase()) {
            case "eq":
                userInfoList = userInfoService.findBy(property, value);
                break;
            case "like":
                userInfoList = userInfoService.findLike(property, value + "%");
                break;
            default:
                throw new Exception("参数错误");
        }
        JSONObject jsonObject = new JSONObject();
        if (userInfoList != null) {
            jsonObject.put("message", "查询成功");
            jsonObject.put("userList", JSON.parse(JsonUtils.toJson(userInfoList)));
        } else {
            jsonObject.put("message", "查询失败");
        }
        return jsonObject;
    }


}
