package com.expressTracking.controller;

import com.expressTracking.entity.UserInfo;
import com.expressTracking.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
     * @return {@code ResponseEntity<UserInfo>, HttpStatus=200, Header={"EntityClass", "UserInfo"}}返回用户信息
     */
    @RequestMapping(value = "/doLogin/{uId}/{pwd}", method = RequestMethod.GET)
    public ResponseEntity<UserInfo> doLogin(@PathVariable("uId") int uId, @PathVariable("pwd") String pwd) {
        UserInfo userInfo = userInfoService.checkLogin(uId, pwd);
        return ResponseEntity.ok().header("EntityClass", "UserInfo").body(userInfo);
    }

    /**
     * 退出登录
     * @return HttpStatus=200 退出登录成功信息
     */
    @RequestMapping(value = "/doLogout")
    public ResponseEntity<String> doLogout() {
        return ResponseEntity.ok().body("成功退出登录");
    }

    /**
     * 查询用户信息
     * @param property 属性 uId or uRull or status
     * @param restrictions 限制条件 eq or like
     * @param value 属性值
     * @return 用户信息集合
     */
    @RequestMapping(value = "/getUserList/{Property}/{Restrictions}/{Value}", method = RequestMethod.GET)
    public List<UserInfo> getUserList(@PathVariable("Property") String property,
                                      @PathVariable("Restrictions") String restrictions,
                                      @PathVariable("Value") String value) {
        List<UserInfo> list = new ArrayList<>();
        if("uId".equals(property) || "uRull".equals(property) || "status".equals(property)){
            switch(restrictions.toLowerCase()){
                case "eq":
                    list = userInfoService.findBy(property, value);
                    break;
                case "like":
                    list = userInfoService.findLike(property, value+"%");
                    break;
                default:
            }
        }else{
            switch(restrictions.toLowerCase()){
                case "eq":
                    list = userInfoService.findBy(property, value);
                    break;
                case "like":
                    list = userInfoService.findLike(property, value+"%");
                    break;
                default:
            }
        }
        return list;
    }

    /**
     * 删除用户
     * @param uId 用户id
     * @return 成功时返回"删除成功",失败时返回"删除失败"
     */
    @RequestMapping(value = "/deleteUserInfo/{uId}", method = RequestMethod.DELETE)
    public ResponseEntity<String> deleteUserInfo(@PathVariable("uId") int uId) {
        if (userInfoService.delete(uId) == 1) {
            return ResponseEntity.ok("删除成功");
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("删除失败");
    }

    /**
     * 添加用户
     * @param userInfo 用户信息
     * @return 返回"添加成功"
     */
    @RequestMapping(value = "/addUserInfo", method = RequestMethod.POST)
    public ResponseEntity<String> addUserInfo(@RequestBody UserInfo userInfo) {
        userInfoService.save(userInfo);
        return ResponseEntity.ok("添加成功");
    }

    /**
     * 更新用户信息
     * @param userInfo 用户信息
     * @return {@code HttpStatus=200, Header={"EntityClass", "E_UserInfo"}}返回用户信息
     */
    @RequestMapping(value = "/updateUserInfo", method = RequestMethod.POST)
    public ResponseEntity<UserInfo> updateUserInfo(@RequestBody UserInfo userInfo) {
        userInfoService.update(userInfo);
        return ResponseEntity.ok().header("EntityClass", "E_UserInfo").body(userInfo);
    }
}
