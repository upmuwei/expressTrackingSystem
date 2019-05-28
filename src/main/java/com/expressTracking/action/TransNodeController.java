package com.expressTracking.action;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.expressTracking.entity.ResponseCode;
import com.expressTracking.entity.TransNode;
import com.expressTracking.service.TransNodeService;
import com.expressTracking.utils.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 网点管理
 * @author muwei
 * @date 2019/5/28
 */
@RestController
@RequestMapping("transNode")
public class TransNodeController {

    private final TransNodeService transNodeService;

    @Autowired
    public TransNodeController(TransNodeService transNodeService) {
        this.transNodeService = transNodeService;
    }


    /**
     * 多条件查询网点
     * @param transNode
     * @return
     */
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public JSONObject getTransNodeList(@RequestBody TransNode transNode) {
        JSONObject jsonObject = new JSONObject();
        ResponseCode code = new ResponseCode();
        if (transNode != null) {
            List<TransNode> transNodeList = transNodeService.getByParameters(transNode);
            if (transNodeList != null) {
                code.setCode(ResponseCode.Result.SUCESS);
                jsonObject.put("transNodeList", JSON.parse(JsonUtils.toJson(transNodeList)));
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
     * 添加网点信息
     *
     * @param transNode
     * @return
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public JSONObject add(@RequestBody TransNode transNode) {
        JSONObject jsonObject = new JSONObject();
        ResponseCode code = new ResponseCode();
        if (transNode != null) {
            if (transNodeService.save(transNode) > 0) {
                code.setCode(ResponseCode.Result.SUCESS);
                jsonObject.put("transNode", JSON.parse(JsonUtils.toJson(transNode)));
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
     * 修改网点信息
     *
     * @param transNode
     * @return
     */
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public JSONObject update(@RequestBody TransNode transNode) {
        JSONObject jsonObject = new JSONObject();
        ResponseCode code = new ResponseCode();
        if (transNode != null) {
            if (transNodeService.update(transNode) > 0) {
                code.setCode(ResponseCode.Result.SUCESS);
                transNode = transNodeService.get(transNode.getId());
                jsonObject.put("transNode", transNode);
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
