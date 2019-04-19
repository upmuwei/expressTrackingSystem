package com.expressTracking.controller;

import com.expressTracking.entity.UserInfo;
import com.expressTracking.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 19231
 * @date 2019/4/5
 */
@RestController
@RequestMapping(value = "/user")
public class UserController {

    private final UserInfoService userInfoService;

    @Autowired
    public UserController(UserInfoService userInfoService) {
        this.userInfoService = userInfoService;
    }


    /**
     * 登录
     * @param uId 用户id
     * @param pwd 密码
     * @return {@code HttpStatus=200, Header={"Type", "Select"}}返回用户信息
     */
    @RequestMapping(value = "/doLogin/{uId}/{pwd}", method = RequestMethod.GET)
    public ResponseEntity<UserInfo> doLogin(HttpSession session, @PathVariable("uId") int uId, @PathVariable("pwd") String pwd) throws Exception {
        UserInfo userInfo = userInfoService.checkLogin(uId, pwd);
        if (userInfo == null) {
            throw new Exception("账号或密码错误");
        }

        session.setAttribute(String.valueOf(uId), userInfo);
        return ResponseEntity.ok().header("session", String.valueOf(uId)).body(userInfo);
    }

    /**
     * 退出登录
     * @return {@code HttpStatus=200, Header={"Type", "Select"}}退出登录成功信息
     */
    @RequestMapping(value = "/doLogout")
    public ResponseEntity<String> doLogout(HttpSession session, HttpServletRequest request) {
        String sessionId = request.getHeader("session");
        session.removeAttribute(sessionId);
        return ResponseEntity.ok().header("Type", "Select")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .body("{\"message\":\"成功退出登录\"}");
    }

    /**
     * 查询用户信息
     * @param property 属性 UID or Name or TelCode or DptID or ReceivePackageID or DelivePackageID or TransPackageID
     * @param restrictions 限制条件 eq or like
     * @param value 属性值
     * @return {@code HttpStatus=200, Header={"Type", "Select"}}用户信息集合
     */
    @RequestMapping(value = "/getUserList/{Property}/{Restrictions}/{Value}", method = RequestMethod.GET)
    public ResponseEntity<List<UserInfo>> getUserList(@PathVariable("Property") String property,
                                      @PathVariable("Restrictions") String restrictions,
                                      @PathVariable("Value") String value) throws Exception {
        List<UserInfo> userInfoList = new ArrayList<>();
        switch(restrictions.toLowerCase()){
            case "eq":
                userInfoList = userInfoService.findBy(property, value);
                break;
            case "like":
                userInfoList = userInfoService.findLike(property, value+"%");
                break;
            default: throw new Exception("参数错误");
            }

        return ResponseEntity.ok().header("Type", "Select").body(userInfoList);
    }

    /**
     * 删除用户
     * @param uId 用户id
     * @return {@code HttpStatus=200, Header={"Type", "Delete"}}"删除成功"
     */
    @RequestMapping(value = "/deleteUserInfo/{uId}", method = RequestMethod.DELETE)
    public ResponseEntity<String> deleteUserInfo(@PathVariable("uId") int uId) {
        if (userInfoService.delete(uId) == 1) {
            return ResponseEntity.ok().header("Type", "Delete").body("{\"message\":\"删除成功\"}");
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("{\"message\":\"删除失败\"}");
    }

    /**
     * 添加用户
     * @param userInfo 用户信息
     * @return {@code HttpStatus=200, Header={"Type", "Save"}}"添加成功"
     */
    @RequestMapping(value = "/addUserInfo", method = RequestMethod.POST)
    public ResponseEntity<UserInfo> addUserInfo(@RequestBody UserInfo userInfo) {
        userInfoService.save(userInfo);
        return ResponseEntity.ok().header("Type", "Save").body(userInfo);
    }

    /**
     * 更新用户信息
     * @param userInfo 用户信息
     * @return {@code HttpStatus=200, Header={"Type", "Update"}}用户信息
     */
    @RequestMapping(value = "/updateUserInfo", method = RequestMethod.POST)
    public ResponseEntity<UserInfo> updateUserInfo(@RequestBody UserInfo userInfo) {
        userInfoService.update(userInfo);
        return ResponseEntity.ok().header("Type", "Update").body(userInfo);
    }
}
