package com.expressTracking.controller;

import com.expressTracking.entity.*;
import com.expressTracking.service.*;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author muwei
 * @date 2019/4/5
 */
@RequestMapping("/domain")
@RestController
public class DomainController {

    private static final Logger LOGGER = LoggerFactory.getLogger(DomainController.class);

    private final ExpressSheetService expressSheetService;

    private final TransPackageService transPackageService;

    private final TransPackageContentService transPackageContentService;

    private final UserInfoService userInfoService;

    private final UserPackageService userPackageService;

    private final TransHistoryService transHistoryService;

    private final TransNodeService transNodeService;

    private final PackageRecordService packageRecordService;

    @Autowired
    public DomainController(ExpressSheetService expressSheetService, TransPackageService transPackageService,
                            TransPackageContentService transPackageContentService, UserInfoService userInfoService,
                            UserPackageService userPackageService, TransHistoryService transHistoryService, TransNodeService transNodeService, PackageRecordService packageRecordService) {
        this.expressSheetService = expressSheetService;
        this.transPackageService = transPackageService;
        this.transPackageContentService = transPackageContentService;
        this.userInfoService = userInfoService;
        this.userPackageService = userPackageService;
        this.transHistoryService = transHistoryService;
        this.transNodeService = transNodeService;
        this.packageRecordService = packageRecordService;
    }

    private Date getCurrentDate() throws Exception {

        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        Date tm = new Date();
        try {
            tm= sdf.parse(sdf.format(new Date()));
        } catch (java.text.ParseException e) {
            e.printStackTrace();
            throw new Exception("获取时间出错");
        }
        return tm;
    }

    /**
     * 得到快递单集合
     * @param property 属性 ID or Sender or Recever or Accepter or Deliver or Status
     * @param restrictions 限定条件 eq or like
     * @param value 属性值
     * @return {@code HttpStatus=200, Header={"Type", "Select"}}快递单集合
     */
    @RequestMapping(value = "/getExpressList/{Property}/{Restrictions}/{Value}", method = RequestMethod.GET)
    public ResponseEntity<List<ExpressSheet>> getExpressList(@PathVariable("Property")String property,
                                             @PathVariable("Restrictions")String restrictions,
                                             @PathVariable("Value")String value) throws Exception {
        List<ExpressSheet> list = new ArrayList<>();
        switch(restrictions.toLowerCase()){
            case "eq":
                list = expressSheetService.findBy(property, value);
                break;
            case "like":
                list = expressSheetService.findLike(property, value+"%");
                break;
            default: throw new Exception("参数错误");
        }
        return ResponseEntity.ok().header("Type", "Select").body(list);
    }

    /**
     * 上传快递图片
     * @param expressId 快件单号
     * @param file 图片文件
     * @return {@code httpStatus=200, header={"Type","Save"}} 图片字节流
     */
    @RequestMapping(value = "uploadExpressImage/{expressId}", method = RequestMethod.POST)
    public ResponseEntity<byte[]> uploadExpressImage(@RequestParam("file") MultipartFile file,
                                                          @PathVariable("expressId") String expressId) throws Exception {
        if (file == null) {
            throw  new Exception("上传文件出错");
        } else {
            FileUtils.copyInputStreamToFile(
                    file.getInputStream(),
                    new File("D:\\expressTracking\\expressSheet\\images\\",
                            expressId));
        }
        return ResponseEntity.ok().header("Type","Save").body(file.getBytes());
    }

    /**
     * 得到快递图片字节流
     * @param expressId 快件单号
     * @return {@code httpStatus=200, header={"Type","Select"}} 图片字节流
     * @throws IOException
     */
    @SuppressWarnings("ResultOfMethodCallIgnored")
    @RequestMapping(value = "getExpressImage/{expressId}", method = RequestMethod.GET)
    public ResponseEntity<byte[]> getExpressImage(@PathVariable("expressId") String expressId) throws IOException {
        String path = "D:\\expressTracking\\expressSheet\\" + expressId;
        FileInputStream file = new FileInputStream(path);
        byte[] buff = new byte[file.available()];
        file.read(buff);
        return ResponseEntity.ok().header("Type", "Select").body(buff);
    }

    /**
     * 得到包裹中的快递单
     * @param packageId 包裹id
     * @return {@code HttpStatus=200, Header={"Type", "Select"}}快递单集合
     */
    @RequestMapping(value = "/getExpressListInPackage/PackageId/{PackageId}", method = RequestMethod.GET)
    public ResponseEntity<List<ExpressSheet>> getExpressListInPackage(@PathVariable("PackageId")String packageId) {
        List<ExpressSheet> list = expressSheetService.getListInPackage(packageId);
        return ResponseEntity.ok().header("Type", "Select").body(list);
    }

    /**
     * 根据快递单号得到快递单
     * @param id 快递单号
     * @return {@code HttpStatus=200, Header={"Type", "Select"}}快递单
     */
    @RequestMapping(value ="/getExpressSheet/{id}", method = RequestMethod.GET)
    public ResponseEntity<ExpressSheet> getExpressSheet(@PathVariable("id")String id) {
        ExpressSheet expressSheet = expressSheetService.get(id);
        return ResponseEntity.ok().header("Type", "Select").body(expressSheet);
    }

    /**
     * 创建快递订单
     * @param expressSheet 快递订单
     * @return {@code HttpStatus=200, Header={"Type", "Save"}}快递订单
     */
    @RequestMapping(value = "/createExpressSheet", method = RequestMethod.POST)
    public ResponseEntity<ExpressSheet> createExpressSheet(@RequestBody ExpressSheet expressSheet) throws Exception {
        SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMddHHmmss");
        Date tm = getCurrentDate();
        String s = sdf.format(tm);
        String id = expressSheet.getSender().getName() + "_" + s;
        expressSheet.setId(id);
        expressSheet.setType(0);
        expressSheet.setStatus(ExpressSheet.STATUS.STATUS_CREATED);
        expressSheetService.save(expressSheet);
        return ResponseEntity.ok().header("Type", "Save").body(expressSheet);
    }

    /**
     * 更新快递订单
     * @param obj 快递订单
     * @return {@code HttpStatus=200, Header={"Type", "Update"}}快递订单
     */
    @RequestMapping(value = "/updateExpressSheet", method = RequestMethod.POST)
    public ResponseEntity<ExpressSheet> updateExpressSheet(@RequestBody ExpressSheet obj) throws Exception {
        ExpressSheet expressSheet = expressSheetService.get(obj.getId());
        if (expressSheet.getStatus() != 0 && expressSheet.getStatus() != 1) {
            throw new Exception("快递已发货，不能更改快递信息");
        }
        expressSheetService.update(obj);
        return ResponseEntity.ok().header("Type", "Update").body(obj);
    }

    /**
     * 揽收
     * @param id 快递id
     * @param uId 快递员id
     * @return {@code HttpStatus=200, Header={"Type", "Save"}}快递处于新建状态返回快递单，
     */
    @RequestMapping(value = "/receiveExpressSheetId/{id}/{uId}", method = RequestMethod.POST)
    public ResponseEntity<ExpressSheet> receiveExpressSheetId(@PathVariable("id")String id,
                                                              @PathVariable("uId")int uId) throws Exception {
        ExpressSheet nes = expressSheetService.get(id);
        if(nes.getStatus() != ExpressSheet.STATUS.STATUS_CREATED){
            throw new Exception("快件运单状态错误!无法收件!");
        }
        nes.setAccepter(String.valueOf(uId));
        nes.setAccepteTime(getCurrentDate());
        nes.setStatus(ExpressSheet.STATUS.STATUS_RECEIVED);
        expressSheetService.update(nes);
        UserInfo userInfo = userInfoService.get(uId);
        TransPackageContent transPackageContent = new TransPackageContent();
        transPackageContent.setPackageId(userInfo.getReceivePackageId());
        transPackageContent.setExpressId(nes.getId());
        transPackageContent.setStatus(TransPackageContent.STATUS.STATUS_ACTIVE);
        transPackageContentService.save(transPackageContent);
        return ResponseEntity.ok().header("Type", "Save").body(nes);
    }


    /**
     * 派送快递
     * @param id 快递id
     * @param uId 派送人id
     * @return {@code HttpStatus=200, Header={"Type", "Update"}}快递单
     */
    @RequestMapping(value = "/dispatchExpressSheetId/{id}/{uId}", method = RequestMethod.POST)
    public ResponseEntity<ExpressSheet> dispatchExpressSheet(@PathVariable("id")String id,
                                                             @PathVariable("uId")int uId) throws Exception {
        TransPackageContent transPackageContent = new TransPackageContent();
        ExpressSheet nes = expressSheetService.get(id);
        if (nes.getStatus() != ExpressSheet.STATUS.STATUS_TRANSPORT) {
            throw new Exception("快递状态信息错误");
        }
        nes.setDeliver(String.valueOf(uId));
        nes.setDeliveTime(getCurrentDate());
        nes.setStatus(ExpressSheet.STATUS.STATUS_DISPATCHED);
        expressSheetService.update(nes);
        transPackageContent.setPackageId(userInfoService.get(uId).getDelivePackageId());
        transPackageContent.setExpressId(nes.getId());
        transPackageContent.setStatus(TransPackageContent.STATUS.STATUS_ACTIVE);
        transPackageContentService.update(transPackageContent);
        return ResponseEntity.ok().header("Type", "Update").body(nes);
    }

    /**
     * 交付快递
     * @param id 快递单id
     * @param uId 派送员id
     * @return {@code HttpStatus=200, Header={"Type", "Update"}}快递单
     */
    @RequestMapping(value = "/deliveryExpressSheetId/{id}/{uId}", method = RequestMethod.POST)
    public ResponseEntity<ExpressSheet> deliveryExpressSheetId(@PathVariable("id")String id,
                                                               @PathVariable("uId")int uId) throws Exception {
        ExpressSheet nes = expressSheetService.get(id);
        if (nes.getStatus() != ExpressSheet.STATUS.STATUS_DISPATCHED) {
            throw new Exception("快递未派送，不能交付");
        }
        nes.setStatus(ExpressSheet.STATUS.STATUS_DELIVERIED);
        expressSheetService.update(nes);
        TransPackageContent transPackageContent = transPackageContentService.findByExpressIdAndStatus(id,
                TransPackageContent.STATUS.STATUS_ACTIVE);
        transPackageContent.setStatus(TransPackageContent.STATUS.STATUS_OUTOF_PACKAGE);
        transPackageContentService.update(transPackageContent);
        return ResponseEntity.ok().header("Type", "Update").body(nes);
    }

    /**
     * 得到包裹集合
     * @param property 属性 ID or SourceNode or TargetNode or Status
     * @param restrictions 限定条件 eq or like
     * @param value 属性值
     * @return {@code HttpStatus=200, Header={"Type", "Select"}}包裹集合
     */
    @RequestMapping(value = "/getTransPackageList/{Property}/{Restrictions}/{Value}", method = RequestMethod.GET)
    public ResponseEntity<List<TransPackage>> getTransPackageList(@PathVariable("Property")String property,
                                                                  @PathVariable("Restrictions")String restrictions,
                                                                  @PathVariable("Value")String value) throws Exception {
        List<TransPackage> list;
        switch(restrictions.toLowerCase()){
            case "eq":
                list = transPackageService.findBy(property, value );
                break;
            case "like":
                list = transPackageService.findLike(property, value+"%");
                break;
                default:throw new Exception("参数错误");
        }
        return ResponseEntity.ok().header("Type", "Select").body(list);
    }

    /**
     * 得到包裹信息
     * @param id 包裹id
     * @return {@code HttpStatus=200, Header={"Type", "Select"}}包裹单
     */
    @RequestMapping(value = "/getTransPackage/{id}", method = RequestMethod.GET)
    public ResponseEntity<TransPackage> getTransPackage(@PathVariable("id")String id) {
        TransPackage es = transPackageService.get(id);
        return ResponseEntity.ok().header("Type", "Select").body(es);
    }

    /**
     * 创建包裹
     * @param transPackage 包裹单
     * @param uId 员工id
     * @return {@code HttpStatus=200, Header={"Type", "Save"}}包裹信息
     */
    @RequestMapping(value = "/newTransPackage/{uId}", method = RequestMethod.POST)
    public ResponseEntity<TransPackage> newTransPackage(@RequestBody TransPackage transPackage,
                                                        @PathVariable("uId") int uId) throws Exception {
        transPackage.setCreateTime(getCurrentDate());
        PackageRecord packageRecord = new PackageRecord();
        packageRecord.setuId(uId);
        packageRecord.setPackageId(transPackage.getId());
        packageRecord.setOperation(0);
        packageRecordService.save(packageRecord);
        transPackageService.save(transPackage);
        return ResponseEntity.ok().header("Type", "Save").body(transPackage);
    }



    /**
     * 拆开包裹
     * @param uId 工作人员id
     * @param id 包裹id
     * @return {@code HttpStatus=200, Header={"Type", "Update"}} "成功信息"
     */
    @RequestMapping(value = "/openTransPackage/{uId}/{id}", method = RequestMethod.GET)
    public ResponseEntity<String> openTransPackage(@PathVariable("uId")int uId,
                                                   @PathVariable("id")String id) throws Exception {
        TransPackage transPackage = transPackageService.get(id);
        if(transPackage.getStatus() == 0){
            throw new Exception("包裹处于新建状态，未装入快件");
        }
        Set<TransPackageContent> transPackageContents = transPackage.getContent();
        if(transPackageContents.isEmpty()){
            throw new Exception("传入包裹id不存在");
        }
        transPackage.setStatus(0);
        transPackageService.update(transPackage);
        PackageRecord packageRecord = new PackageRecord();
        packageRecord.setPackageId(transPackage.getId());
        packageRecord.setuId(uId);
        packageRecord.setOperation(3);
        packageRecordService.save(packageRecord);
        for (TransPackageContent transPackageContent : transPackageContents) {
            if (transPackageContent.getStatus() == 1) {
                continue;
            }
            transPackageContent.setStatus(TransPackageContent.STATUS.STATUS_OUTOF_PACKAGE);
            transPackageContentService.update(transPackageContent);
            ExpressSheet expressSheet = expressSheetService.get(transPackageContent.getExpressId());
            expressSheet.setStatus(ExpressSheet.STATUS.STATUS_PARTATION);
            expressSheetService.update(expressSheet);
        }
        return ResponseEntity.ok().header("Type", "Update")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .body("{\"message\":\"打开包裹成功\"}");
    }


    /**
     * 转运包裹
     * @param transPackageId 包裹id
     * @param uId 转运人员id
     * @return {@code Body="Success", HttpStatus=200, Header={"Type", "Update"}} 成功消息
     */
    @RequestMapping(value = "/deliveryTransPackage/{transPackageId}/{uId}", method = RequestMethod.POST)
    public ResponseEntity<String> deliveryTransPackage(@PathVariable("transPackageId") String transPackageId,
                                                       @PathVariable("uId")int uId) throws Exception {
        UsersPackage usersPackage = userPackageService.findByPackageId(transPackageId);
        if (usersPackage.getUserUid() != uId) {
            throw new Exception("转运人员错误，不能转运");
        }
        TransPackage transPackage = transPackageService.get(transPackageId);
        if (transPackage.getStatus() != 1) {
            throw new Exception("包裹状态信息错误,不能转运");
        }
        transPackage.setStatus(2);
        transPackageService.update(transPackage);
        PackageRecord packageRecord = new PackageRecord();
        packageRecord.setuId(uId);
        packageRecord.setPackageId(transPackage.getId());
        packageRecord.setOperation(2);
        packageRecordService.save(packageRecord);
        Set<TransPackageContent> transPackageContents = transPackage.getContent();
        for(TransPackageContent transPackageContent:transPackageContents) {
            ExpressSheet expressSheet = expressSheetService.get(transPackageContent.getExpressId());
            expressSheet.setStatus(ExpressSheet.STATUS.STATUS_TRANSPORT);
            expressSheetService.update(expressSheet);
        }
        return ResponseEntity.ok().header("Type", "Update")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .body("{\"message\":\"Success\"}");
    }

    /**
     * 确认包裹到达目的站点
     * @param transPackageId 转运包裹id
     * @param userId1 转运人员id
     * @param userId2 扫描员id
     * @return {@code Body="Success", HttpStatus=200, Header={"Type", "Update"}} 成功消息
     */
    @RequestMapping(value = "/arriveDestination/{transPackageId}/{userId1}/{userId2}", method = RequestMethod.POST)
    public ResponseEntity<String> arriveDestination(@PathVariable("transPackageId") String transPackageId,
                                               @PathVariable("userId1")int userId1,
                                               @PathVariable("userId2") int userId2) throws Exception {
        UsersPackage usersPackage = userPackageService.findByPackageId(transPackageId);
        UserInfo userInfo = userInfoService.get(userId2);
        TransNode transNode = transNodeService.get(userInfo.getDptId());
        TransHistory transHistory = new TransHistory();
        if (usersPackage == null) {
            throw new Exception("未查到包裹Id");
        } else{
            userPackageService.remove(usersPackage.getSn());
            usersPackage.setUserUid(userId2);
            usersPackage.setSn(0);
            userPackageService.save(usersPackage);
            transHistory.setActTime(getCurrentDate());
            transHistory.setuIdFrom(userId1);
            transHistory.setuIdTo(userId2);
            transHistory.setX(transNode.getX());
            transHistory.setY(transNode.getY());
            transHistory.setPackageId(transPackageId);
            transHistoryService.save(transHistory);
            return ResponseEntity.ok().header("Type", "Update")
                    .contentType(MediaType.APPLICATION_JSON_UTF8)
                    .body("{\"message\":\"Success\"}");
        }
    }


    /**
     * 确认打包完成
     * @param transPackageId 包裹id
     * @param uId 员工Id
     * @return {@code HttpStatus=200, Header={"Type", "Save"}}包裹内容
     */
    @RequestMapping(value = "/packTransPackage/{transPackageId}/{uId}", method = RequestMethod.POST)
    public ResponseEntity<PackageRecord> packOk(@PathVariable("transPackageId")String transPackageId,
                                                                @PathVariable("uId")int uId) throws Exception {
        PackageRecord packageRecord = new PackageRecord();
        packageRecord.setPackageId(transPackageId);
        packageRecord.setuId(uId);
        packageRecord.setOperation(1);
        return ResponseEntity.ok().header("Type", "Save")
                .body(packageRecord);
    }

    /**
     * 打包
     * @param transPackageId 包裹id
     * @param expressSheetId 快递id
     * @return {@code HttpStatus=200, Header={"Type", "Save"}}包裹内容
     */
    @RequestMapping(value = "/packTransPackage/{transPackageId}/{expressSheetId}", method = RequestMethod.POST)
    public ResponseEntity<TransPackageContent> packTransPackage(@PathVariable("transPackageId")String transPackageId,
                                                                @PathVariable("expressSheetId")String expressSheetId) throws Exception {
        TransPackage transPackage = transPackageService.get(transPackageId);
        if (transPackage.getStatus() == 1 || transPackage.getStatus() == 2) {
            throw new Exception("包裹状态信息错误");
        }
        if (transPackage.getStatus() == 0) {
            transPackage.setStatus(1);
        }
        transPackageService.update(transPackage);
        TransPackageContent transPackageContent = new TransPackageContent();
        transPackageContent.setPackageId(transPackageId);
        transPackageContent.setExpressId(expressSheetId);
        transPackageContent.setStatus(TransPackageContent.STATUS.STATUS_ACTIVE);
        transPackageContentService.save(transPackageContent);
        return ResponseEntity.ok().header("Type", "Save")
                .body(transPackageContent);
    }

    /**
     * 根据工作人员id得到与之相关联的包裹信息
     * @param userUId 工作人员
     * @return {@code HttpStatus=200, Header={"Type", "Select"}}包裹信息集合
     */
    @RequestMapping(value = "/getUserPackage/{userUId}", method = RequestMethod.GET)
    public ResponseEntity<List<TransPackage>> getUsersPackage(@PathVariable("userUId")int userUId) {
        List<UsersPackage> usersPackageList = userPackageService.getTransPackageList(userUId);
        List<TransPackage> transPackageList  = new ArrayList<>();
        for (UsersPackage usersPackage : usersPackageList) {
            TransPackage transPackage = transPackageService.get(usersPackage.getPackageId());
            transPackageList.add(transPackage);
        }
        return ResponseEntity.ok().header("Type", "Select").body(transPackageList);
    }

    /**
     * 得到包裹操作记录
     * @param packageId 包裹Id
     * @return {@code HttpStatus=200, Header={"Type","Select"}} 包裹操作记录集合
     */
    @RequestMapping(value = "getPackageRecordByPackageId/{packageId}", method = RequestMethod.GET)
    public ResponseEntity<List<PackageRecord>> getPackageRecordByPackageId(@PathVariable("packageId")String packageId) {
        List<PackageRecord> packageRecords = packageRecordService.findByPackageId(packageId);
        return ResponseEntity.ok().header("Type", "Select").body(packageRecords);
    }

    /**
     * 得到包裹操作记录
     * @param uId 员工Id
     * @return {@code HttpStatus=200, Header={"Type","Select"}} 包裹操作记录集合
     */
    @RequestMapping(value = "getPackageRecordByuId/{uId}", method = RequestMethod.GET)
    public ResponseEntity<List<PackageRecord>> getPackageRecordByuId(@PathVariable("uId") int uId) {
        List<PackageRecord> packageRecords = packageRecordService.findByuId(uId);
        return ResponseEntity.ok().header("Type", "Select").body(packageRecords);
    }

}
