package com.expressTracking.action;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.expressTracking.entity.ResponseCode;
import com.expressTracking.entity.UserInfo;
import com.expressTracking.service.UserInfoService;
import com.expressTracking.utils.JsonUtils;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
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
        JSONObject jsonObject = new JSONObject(ResponseCode.Type.ADD);
        ResponseCode code = new ResponseCode();
        if (userInfo != null && userInfo.getTelCode() != null) {
            if (userInfoService.getUserByTelCode(userInfo.getTelCode()) != null) {
                code.setCode(ResponseCode.Result.FAIL);
                code.setMessage("该用户已注册");
            } else if (userInfoService.save(userInfo) > 0) {
                code.setCode(ResponseCode.Result.SUCESS);
//                userInfo = userInfoService.getUserByTelCode(userInfo.getTelCode());
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
     * 用户登录
     *
     * @param map
     * @param session
     * @return
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public JSONObject doLogin(@RequestBody Map<String, String> map, HttpSession session) {
        ResponseCode code = new ResponseCode(ResponseCode.Type.SELECT);
        String phone = map.get("phone");
        String password = map.get("password");
        UserInfo userInfo = userInfoService.checkLogin(phone, password);
        JSONObject jsonObject = new JSONObject();
        System.out.println(jsonObject.toString());
        if (userInfo != null) {
            code.setCode(ResponseCode.Result.SUCESS);
            jsonObject.put("user", JSON.parse(JsonUtils.toJson(userInfo)));
            String sessionId = userInfo.getTelCode().hashCode() + "" + System.currentTimeMillis();
            jsonObject.put("sessionId", sessionId);
//            session.setAttribute(userInfo.getuId() + "", session);
            session.setAttribute(sessionId, userInfo);
        } else {
            code.setCode(ResponseCode.Result.FAIL);
        }
        jsonObject.put("code", JSON.parse(JsonUtils.toJson(code)));
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
        ResponseCode code = new ResponseCode();

        UserInfo userInfo = userInfoService.get(userId);
        JSONObject jsonObject = new JSONObject();
        if (userInfo != null) {
            jsonObject.put("user", JSON.parse(JsonUtils.toJson(userInfo)));
        } else {
            code.setCode(ResponseCode.Result.FAIL);
            code.setMessage("没有该用户信息");
        }
        jsonObject.put("code", JSON.parse(JsonUtils.toJson(code)));
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
    public JSONObject logout(@PathVariable("userId") Integer userId, HttpSession session, HttpHeaders headers) {
        ResponseCode code = new ResponseCode();
        UserInfo userInfo = userInfoService.get(userId);
        JSONObject jsonObject = new JSONObject();
        if (userInfo != null) {
//            session.removeAttribute(userId + "");
            code.setCode(ResponseCode.Result.SUCESS);
            String sessionId = headers.getFirst("sessionId");
            session.removeAttribute(sessionId);
            code.setMessage("用户" + userInfo.getTelCode() + "退出登录");
        } else {
            code.setCode(ResponseCode.Result.FAIL);
            code.setMessage("用户" + userId + "不存在");
        }
        jsonObject.put("code", JSON.parse(JsonUtils.toJson(code)));
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
        ResponseCode code = new ResponseCode();
        JSONObject jsonObject = new JSONObject();
        if (userInfoService.update(userInfo) > 0) {
            code.setCode(ResponseCode.Result.SUCESS);
        } else {
            //失败了
            code.setCode(ResponseCode.Result.FAIL);
            code.setMessage("用户信息修改失败");
        }
        jsonObject.put("code", JSON.parse(JsonUtils.toJson(code)));
        return jsonObject;
    }

    /**
     * 修改用户密码
     *
     * @param phone
     * @param password
     * @return
     */
    @RequestMapping(value = "/forget_password",method = RequestMethod.POST)
    public JSONObject forgetPassword(String phone, String password) {
        JSONObject jsonObject = new JSONObject();
        ResponseCode code = new ResponseCode();
        if (phone == null || password == null) {
            UserInfo userInfo = userInfoService.getUserByTelCode(phone);
            if (userInfo == null) {
                //密码修改失败
                code.setCode(ResponseCode.Result.FAIL);
                code.setMessage("该电话号码未注册");
            } else if (userInfoService.updatePassword(userInfo.getuId(), password) > 0) {
                //密码修改成功
                code.setCode(ResponseCode.Result.SUCESS);
            }
        } else {
            //出错了，哈哈哈
            code.setCode(ResponseCode.Result.ERROR);
            code.setMessage("参数错误");
        }
        jsonObject.put("code", JSON.parse(JsonUtils.toJson(code)));
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
        ResponseCode code = new ResponseCode();
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
            code.setCode(ResponseCode.Result.SUCESS);
            jsonObject.put("userList", JSON.parse(JsonUtils.toJson(userInfoList)));
        } else {
            code.setCode(ResponseCode.Result.FAIL);
        }
        jsonObject.put("code", JSON.parse(JsonUtils.toJson(code)));
        return jsonObject;
    }
}
