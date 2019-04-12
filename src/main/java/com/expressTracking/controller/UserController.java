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
     * @return {@code ResponseEntity<UserInfo>, HttpStatus=200, Header={"EntityClass", "UserInfo"}}成功时返回用户信息,
     * {@code ResponseEntity<String>, HttpStatus=500}失败时返回失败信息
     */
    @RequestMapping(value = "/doLogin/{uId}/{pwd}", method = RequestMethod.GET)
    public ResponseEntity doLogin(@PathVariable("uId") int uId, @PathVariable("pwd") String pwd) {
        try{
            UserInfo userInfo = userInfoService.checkLogin(uId, pwd);
            return ResponseEntity.ok().header("EntityClass", "UserInfo").body(userInfo);
        }
        catch(Exception e)
        {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
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
        int intValue;
        if("uId".equals(property) || "uRull".equals(property) || "status".equals(property)){
            intValue = Integer.parseInt(value);
            switch(restrictions.toLowerCase()){
                case "eq":
                    list = userInfoService.findBy(property, intValue, "uId", true);
                    break;
                case "like":
                    list = userInfoService.findLike(property, intValue+"%", "uId", true);
                    break;
                default:
            }
        }else{
            switch(restrictions.toLowerCase()){
                case "eq":
                    list = userInfoService.findBy(property, value, "uId", true);
                    break;
                case "like":
                    list = userInfoService.findLike(property, value+"%", "uId", true);
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
     * @return 成功时返回"添加成功",失败时返回异常信息
     */
    @RequestMapping(value = "/addUserInfo", method = RequestMethod.POST)
    public ResponseEntity<String> addUserInfo(@RequestBody UserInfo userInfo) {
        try {
            userInfoService.save(userInfo);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
        return ResponseEntity.ok("添加成功");
    }

    /**
     * 更新用户信息
     * @param userInfo 用户信息
     * @return 成功时返回"更新成功",失败时返回异常信息
     */
    @RequestMapping(value = "/updateUserInfo", method = RequestMethod.POST)
    public ResponseEntity<String> updateUserInfo(@RequestBody UserInfo userInfo) {
        try {
            userInfoService.update(userInfo);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
        return ResponseEntity.ok("更新成功");
    }
}
