package com.expressTracking.controller;

import com.expressTracking.entity.Account;
import com.expressTracking.entity.UserInfo;
import com.expressTracking.service.UserInfoService;
import com.expressTracking.utils.MD5Utils;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @author muwei
 * @date 2019/4/5
 */
@RestController
@RequestMapping(value = "/user")
public class UserController {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    private final UserInfoService userInfoService;

    @Autowired
    public UserController(UserInfoService userInfoService) {
        this.userInfoService = userInfoService;
    }


    /**
     * 登录
     * @return {@code HttpStatus=200, Header={"session", "String.valueOf(uId)"}}返回用户信息
     */
    @RequestMapping(value = "/doLogin", method = RequestMethod.POST)
    public ResponseEntity<UserInfo> doLogin(HttpSession session, @RequestBody Account account) throws Exception {
        UserInfo userInfo = userInfoService.checkLogin(account.getId(), account.getPassword());
        if (userInfo == null) {
            throw new Exception("账号或密码错误");
        }
        LOGGER.info(userInfo.getuId() + userInfo.getName() + "登录");
        String sessionId = account.getId() + System.currentTimeMillis();
        session.setAttribute(sessionId, userInfo);
        return ResponseEntity.ok().header("session", sessionId).body(userInfo);
    }

    /**
     * 退出登录
     * @return {@code HttpStatus=200, Header={"Type", "Select"}}退出登录成功信息
     */
    @RequestMapping(value = "/doLogout", method = RequestMethod.GET)
    public ResponseEntity<String> doLogout(HttpSession session, HttpServletRequest request) {
        String sessionId = request.getHeader("session");
        UserInfo userInfo = (UserInfo) session.getAttribute(sessionId);
        LOGGER.info(userInfo.getuId() + userInfo.getName() + "退出登录");
        session.removeAttribute(sessionId);
        return ResponseEntity.ok().header("Type", "Select")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .body("{\"message\":\"成功退出登录\"}");
    }

    /**
     * 查询员工信息
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
     * 删除员工
     * @param uId 员工id
     * @return {@code HttpStatus=200, Header={"Type", "Delete"}} "删除成功"
     */
    @RequestMapping(value = "/deleteUserInfo/{uId}", method = RequestMethod.GET)
    public ResponseEntity<String> deleteUserInfo(@PathVariable("uId") int uId) {
        if (userInfoService.delete(uId) == 1) {
            return ResponseEntity.ok().header("Type", "Delete").body("{\"message\":\"删除成功\"}");
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("{\"message\":\"删除失败\"}");
    }

    /**
     * 添加员工
     * @param userInfo 员工信息
     * @return {@code HttpStatus=200, Header={"Type", "Save"}}"员工电话号码"
     */
    @RequestMapping(value = "/addUserInfo", method = RequestMethod.POST)
    public ResponseEntity<String> addUserInfo(@RequestBody UserInfo userInfo) throws Exception{
        if (userInfoService.save(userInfo) == 0) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .header("Type", "Error")
                    .contentType(MediaType.APPLICATION_JSON_UTF8)
                    .body("{\"message\":\"此用户已存在\"}");
        }
        return ResponseEntity.ok().header("Type", "Save")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .body("{\"message\":\"" + userInfo.getTelCode() + "\"}");
    }

    /**
     * 更新员工信息
     * @param userInfo 员工信息
     * @return {@code HttpStatus=200, Header={"Type", "Update"}} 员工电话号码
     */
    @RequestMapping(value = "/updateUserInfo", method = RequestMethod.POST)
    public ResponseEntity<String> updateUserInfo(@RequestBody UserInfo userInfo) throws Exception {
        if(userInfoService.update(userInfo) == 0) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .header("Type", "Error")
                    .contentType(MediaType.APPLICATION_JSON_UTF8)
                    .body("{\"message\":\"更新失败\"}");
        }
        return ResponseEntity.ok().header("Type", "Update")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .body("{\"message\":\"" + userInfo.getTelCode() + "\"}");
    }

    /**
     * 上传员工头像图片
     * @param uId 员工Id
     * @param file 图片文件
     * @return {@code httpStatus=200, header={"Type","Save"}} 图片地址
     */
    @RequestMapping(value = "uploadUserInfoImage/{uId}", method = RequestMethod.POST)
    public ResponseEntity<String> uploadExpressImage(@RequestParam("file") MultipartFile file,
                                                     @PathVariable("uId") String uId) throws Exception {
        String path = "D:\\expressTracking\\images\\userInfo\\" + uId;
        if (file == null) {
            throw  new Exception("上传文件出错");
        } else {
            FileUtils.copyInputStreamToFile(
                    file.getInputStream(),
                    new File(path));
        }
        return ResponseEntity.ok().header("Type","Save").body(path);
    }

    /**
     * 得到图片地址
     * @param uId 员工Id
     * @return {@code httpStatus=200, header={"Type","Select"}} 图片字节流
     */
    @RequestMapping(value = "getCustomInfoImage/{uId}", method = RequestMethod.GET)
    public ResponseEntity<String> getExpressImage(@PathVariable("uId") String uId) {
        String path = "D:\\expressTracking\\userInfo\\images\\" + uId;
        File file = new File(path);
        if (!file.exists()) {
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body("{\"message\":\"资源不存在\"}");
        }
        return ResponseEntity.ok().header("Type", "Select").body(path);
    }
}
