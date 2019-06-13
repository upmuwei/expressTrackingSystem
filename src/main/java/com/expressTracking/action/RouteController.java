package com.expressTracking.action;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.expressTracking.entity.PackageRoute;
import com.expressTracking.entity.ResponseCode;
import com.expressTracking.exception.ServiceException;
import com.expressTracking.service.PackageRouteService;
import com.expressTracking.utils.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @ClassName RouteController
 * @Decsription TODO 位置信息的controller
 * @Author liwei
 * @Date 2019/5/13 10:13
 * @Version 1.0
 */
@RestController
@RequestMapping("/route")
public class RouteController {

    @Autowired
    private PackageRouteService packageRouteService;

    /**
     * 保存位置信息
     *
     * @param userId 用户编号
     * @param x      位置 x
     * @param y      位置 y
     * @return
     */
    @RequestMapping(value = "/save", method = RequestMethod.GET)
    public JSONObject save(Integer userId, Float x, Float y) throws ServiceException, Exception {
        JSONObject jsonObject = new JSONObject();
        ResponseCode code = new ResponseCode();
        code.setCode(ResponseCode.Result.FAIL);
        System.out.println("userId = " + userId + " x=" + x + " y=" + y);

        if (userId != null && x != null && y != null) {
            if (packageRouteService.save(userId, x, y, null) > 0) {
                code.setCode(ResponseCode.Result.SUCESS);
            }
        } else {
            code.setCode(ResponseCode.Result.ERROR);
            code.setMessage("参数错误");
        }
        jsonObject.put("code", code);
        return jsonObject;
    }

    /**
     * 获取去包裹或快件的位置信息
     *
     * @param type es 查询快件位置信息 package 查询包裹位置信息
     * @param id   快件的ID  或 包裹的ID
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/getRouteList/{type}/{id}", method = RequestMethod.GET)
    public JSONObject getPackageRoute(@PathVariable("type") String type, @PathVariable("id") String id) throws ServiceException, Exception {
        JSONObject jsonObject = new JSONObject();
        ResponseCode code = new ResponseCode();
        code.setCode(ResponseCode.Result.FAIL);
        System.out.println("getRouteList=" + id);
        if (type != null && id != null) {
            List<PackageRoute> routeList = new ArrayList<>();
            switch (type) {
                case "es": {
                    routeList = packageRouteService.getExpressSheetRouteList(id);
                    break;
                }
                case "package": {
                    routeList = packageRouteService.getPackageRouteList(id);
                    break;
                }
                default: {
                    break;
                }
            }
            code.setCode(ResponseCode.Result.SUCESS);
            jsonObject.put("routeList", JSON.parse(JsonUtils.toJson(routeList)));
        } else {
            code.setCode(ResponseCode.Result.ERROR);
            code.setMessage("参数错误");
        }
        jsonObject.put("code", code);
        return jsonObject;
    }
}
