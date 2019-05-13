package com.expressTracking.action;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.expressTracking.entity.CustomerInfo;
import com.expressTracking.entity.ResponseCode;
import com.expressTracking.service.CustomerInfoService;
import com.expressTracking.utils.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @ClassName CustomerController
 * @Decsription TODO 客户信息的控制类
 * @Author liwei
 * @Date 2019/5/7 15:38
 * @Version 1.0
 */
@RestController
@RequestMapping("/customer")
public class CustomerController {
    @Autowired
    private CustomerInfoService customerInfoService;

    /**
     * 添加客户信息
     *
     * @param customerInfo
     * @return
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public JSONObject add(@RequestBody CustomerInfo customerInfo) {
        JSONObject jsonObject = new JSONObject();
        ResponseCode code = new ResponseCode();
        if (customerInfo != null) {
            if (customerInfoService.save(customerInfo) > 0) {
                code.setCode(ResponseCode.Result.SUCESS);
                jsonObject.put("customer", JSON.parse(JsonUtils.toJson(customerInfo)));
            } else {
                code.setCode(ResponseCode.Result.FAIL);
            }
        } else {
            code.setCode(ResponseCode.Result.ERROR);
            code.setMessage("参数错误");
        }
        jsonObject.put("code", code);
        return jsonObject;
    }

    /**
     * 通过ID查找客户信息
     *
     * @param id
     * @return
     */
    @RequestMapping("/get/{id}")
    public JSONObject getById(@PathVariable("id") Integer id) {
        JSONObject jsonObject = new JSONObject();
        ResponseCode code = new ResponseCode();
        if (id != null) {
            CustomerInfo customerInfo = customerInfoService.get(id);
            if (customerInfo != null) {
                code.setCode(ResponseCode.Result.SUCESS);
                jsonObject.put("customer", JSON.parse(JsonUtils.toJson(customerInfo)));
            } else {
                code.setCode(ResponseCode.Result.FAIL);
            }
        } else {
            code.setCode(ResponseCode.Result.ERROR);
            code.setMessage("参数错误");
        }
        jsonObject.put("code", code);
        return jsonObject;
    }

    /**
     * 通过姓名，电话号码，地区编码查找客户信息
     *
     * @param name
     * @param phone
     * @param regionCode
     * @return
     */
    @RequestMapping("/list")
    public JSONObject getCustomerList(String name, String phone, String regionCode) {
        JSONObject jsonObject = new JSONObject();
        ResponseCode code = new ResponseCode();
        CustomerInfo customerInfo = new CustomerInfo();
        customerInfo.setName(name);
        customerInfo.setTelCode(phone);
        customerInfo.setRegionCode(regionCode);
        List<CustomerInfo> customerInfoList = customerInfoService.findByParameter(customerInfo);
        code.setCode(ResponseCode.Result.SUCESS);
        jsonObject.put("customerList", JSON.parse(JsonUtils.toJson(customerInfoList)));
        jsonObject.put("code", code);
        return jsonObject;
    }

    /**
     * 修改客户信息
     *
     * @param customerInfo
     * @return
     */
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public JSONObject update(@RequestBody CustomerInfo customerInfo) {
        JSONObject jsonObject = new JSONObject();
        ResponseCode code = new ResponseCode();
        if (customerInfo != null && customerInfo.getId() != 0) {
            if (customerInfoService.update(customerInfo) > 0) {
                code.setCode(ResponseCode.Result.SUCESS);
                customerInfo = customerInfoService.get(customerInfo.getId());
                jsonObject.put("customer", customerInfo);
            } else {
                code.setCode(ResponseCode.Result.FAIL);
                code.setMessage("修改失败");
            }
        } else {
            code.setCode(ResponseCode.Result.ERROR);
            code.setMessage("参数错误");
        }

        jsonObject.put("code", code);
        return jsonObject;
    }
}
