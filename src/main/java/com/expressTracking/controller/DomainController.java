package com.expressTracking.controller;

import com.expressTracking.entity.*;
import com.expressTracking.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author 19231
 * @date 2019/4/5
 */
@RequestMapping("/domain")
@RestController
public class DomainController {

    private final ExpressSheetService expressSheetService;

    private final TransPackageService transPackageService;

    private final TransPackageContentService transPackageContentService;

    private final UserInfoService userInfoService;

    private final UserPackageService userPackageService;

    private final TransHistoryService transHistoryService;

    private Logger logger = LoggerFactory.getLogger(DomainController.class);

    @Autowired
    public DomainController(ExpressSheetService expressSheetService, TransPackageService transPackageService,
                            TransPackageContentService transPackageContentService, UserInfoService userInfoService,
                            UserPackageService userPackageService, TransHistoryService transHistoryService) {
        this.expressSheetService = expressSheetService;
        this.transPackageService = transPackageService;
        this.transPackageContentService = transPackageContentService;
        this.userInfoService = userInfoService;
        this.userPackageService = userPackageService;
        this.transHistoryService = transHistoryService;
    }

    private Date getCurrentDate() {

        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        Date tm = new Date();
        try {
            tm= sdf.parse(sdf.format(new Date()));
        } catch (java.text.ParseException e) {
            e.printStackTrace();
        }
        return tm;
    }

    /**
     * 得到快递单集合
     * @param property 属性
     * @param restrictions 限定条件 eq or like
     * @param value 属性值
     * @return {@code List<ExpressSheet>}快递单集合
     */
    @RequestMapping(value = "/getExpressList/{Property}/{Restrictions}/{Value}", method = RequestMethod.GET)
    public List<ExpressSheet> getExpressList(@PathVariable("Property")String property,
                                             @PathVariable("Restrictions")String restrictions,
                                             @PathVariable("Value")String value) {
        List<ExpressSheet> list = new ArrayList<>();
        switch(restrictions.toLowerCase()){
            case "eq":
                list = expressSheetService.findBy(property, value, "ID", true);
                break;
            case "like":
                list = expressSheetService.findLike(property, value+"%", "ID", true);
                break;
            default:
        }
        return list;
    }

    /**
     * 得到包裹中的快递单
     * @param packageId 包裹id
     * @return {@code List<ExpressSheet>}快递单集合
     */
    @RequestMapping(value = "/getExpressListInPackage/PackageId/{PackageId}", method = RequestMethod.GET)
    public List<ExpressSheet> getExpressListInPackage(@PathVariable("PackageId")String packageId) {
        List<ExpressSheet> list = new ArrayList<>();
        list = expressSheetService.getListInPackage(packageId);
        return list;
    }

    /**
     * 根据快递单号得到快递单
     * @param id 快递单号
     * @return {@code ResponseEntity<ExpressSheet>, HttpStatus=200, Header={"EntityClass", "ExpressSheet"}}快递单
     */
    @RequestMapping(value ="/getExpressSheet/{id}", method = RequestMethod.GET)
    public ResponseEntity<ExpressSheet> getExpressSheet(@PathVariable("id")String id) {
        ExpressSheet expressSheet = expressSheetService.get(id);
        return ResponseEntity.ok().header("EntityClass", "ExpressSheet").body(expressSheet);
    }

 /*   *
     * 新建快递订单
     * @param id 快递单号
     * @param uid 使用者id
     * @return {@code ResponseEntity<ExpressSheet>, HttpStatus=200, Header={"EntityClass", "ExpressSheet"}}成功时返回快递单，
     * {@code ResponseEntity<String>, HttpStatus=500}异常时返回异常信息
     * {@code ResponseEntity<String>，HttpStatus=200， Header={"EntityClass", "E_ExpressSheet"}}订单已经存在时

    @RequestMapping(value = "/newExpressSheet/id/{id}/uid/{uid}", method = RequestMethod.GET)
    public ResponseEntity newExpressSheet(@PathVariable("id")String id, @PathVariable("uid")int uid) {
        ExpressSheet es = null;
        try{
            es = expressSheetService.get(id);
        } catch (Exception e1) {
            logger.error(e1.getMessage());
        }
        if(es != null) {
            return ResponseEntity.ok().header("EntityClass", "E_ExpressSheet")
                    .body("快件运单信息已经存在!\n无法创建!");
        }
        try{
            ExpressSheet nes = new ExpressSheet();
            nes.setId(id);
            nes.setType(0);
            nes.setAccepter(String.valueOf(uid));
            nes.setAccepteTime(getCurrentDate());
            nes.setStatus(ExpressSheet.STATUS.STATUS_CREATED);
            expressSheetService.save(nes);
            return ResponseEntity.ok().header("EntityClass", "ExpressSheet").body(nes);
        }
        catch(Exception e)
        {
            logger.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }*/


    /**
     * 保存快递订单
     * @param obj 快递单
     * @return {@code ResponseEntity<ExpressSheet>, HttpStatus=200, Header={"EntityClass", "R_ExpressSheet"}}成功时返回快递单，
     * {@code ResponseEntity<String>, HttpStatus=500}异常时返回异常信息
     * {@code ResponseEntity<String>，HttpStatus=200， Header={"EntityClass", "E_ExpressSheet"}}订单已经付运时
     */
    @RequestMapping(value = "/saveExpressSheet", method = RequestMethod.POST)
    public ResponseEntity saveExpressSheet(@RequestBody ExpressSheet obj) {
        try{
            if(obj.getStatus() != ExpressSheet.STATUS.STATUS_CREATED){
                return ResponseEntity.ok().header("EntityClass", "E_ExpressSheet")
                        .body("快件运单已付运!无法保存更改!");
            }
            expressSheetService.save(obj);
            return ResponseEntity.ok().header("EntityClass", "R_ExpressSheet").body(obj);
        }
        catch(Exception e)
        {
            logger.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    /**
     *创建快递订单
     * @param expressSheet 快递订单
     * @return {@code ResponseEntity<String>, HttpStatus=200, Header={"EntityClass", "id"}}成功时返回快递单号，
     * {@code ResponseEntity<String>, HttpStatus=500}异常时返回异常信息
     */
    @RequestMapping(value = "/createExpressSheet", method = RequestMethod.POST)
    public ResponseEntity createExpressSheet(@RequestBody ExpressSheet expressSheet) {
        CustomerInfo snd = expressSheet.getSender();
        CustomerInfo rcv = expressSheet.getRecever();
        SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMddHHmmss");
        Date tm = getCurrentDate();
        String s = sdf.format(tm);
        try{
            String pkgId = userInfoService.get(1).getReceivePackageId();
            ExpressSheet nes = new ExpressSheet();
            String id = snd.getName() + "_" + s;
            nes.setId(id);
            nes.setSender(snd);
            nes.setRecever(rcv);
            nes.setType(0);
            nes.setAccepter(String.valueOf(1));
            nes.setStatus(ExpressSheet.STATUS.STATUS_CREATED);
            expressSheetService.save(nes);
            TransPackageContent pkgAdd = new TransPackageContent();
            pkgAdd.setExpressId(nes.getId());
            pkgAdd.setPackageId(pkgId);
            pkgAdd.setStatus(TransPackageContent.STATUS.STATUS_ACTIVE);
            transPackageContentService.save(pkgAdd);
            return ResponseEntity.ok().header("EntityClass", "id").body(id);
        }
        catch(Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    /**
     * 更新快递订单
     * @param id 原快递订单id
     * @param obj 快递订单
     * @return {@code ResponseEntity<ExpressSheet>, HttpStatus=200, Header={"EntityClass", "R_ExpressSheet"}}成功时返回快递单，
     * {@code ResponseEntity<String>, HttpStatus=500}异常时返回异常信息
     */
    @RequestMapping(value = "/updateExpressSheet/{id}", method = RequestMethod.POST)
    public ResponseEntity updateExpressSheet(@PathVariable("id")String id, @RequestBody ExpressSheet obj) {
        try{
            expressSheetService.save(obj);
            List<TransPackageContent> list = new ArrayList<>();
            list = transPackageContentService.findByExpressId(id);
            for(TransPackageContent transPackageContent : list){
                transPackageContent.setExpressId(obj.getId());
                transPackageContentService.update(transPackageContent);
            }
            expressSheetService.removeById(id);
            return ResponseEntity.ok().header("EntityClass", "R_ExpressSheet").body(obj);
        }
        catch(Exception e)
        {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    /**
     * 揽收
     * @param id 快递id
     * @param uId 快递员id
     * @return {@code ResponseEntity<ExpressSheet>, HttpStatus=200, Header={"EntityClass", "ExpressSheet"}}快递处于新建状态返回快递单，
     * {@code ResponseEntity<String>, HttpStatus=500}异常时返回异常信息
     * {@code ResponseEntity<String>, HttpStatus=200, Header={"EntityClass", "E_ExpressSheet"}} 快递不处于新建状态返回"快件运单状态错误!无法收件!"
     */
    @RequestMapping(value = "/receiveExpressSheetId/id/{id}/uid/{uid}", method = RequestMethod.GET)
    public ResponseEntity receiveExpressSheetId(@PathVariable("id")String id, @PathVariable("uId")int uId){
        try{
            ExpressSheet nes = expressSheetService.get(id);
            if(nes.getStatus() != ExpressSheet.STATUS.STATUS_CREATED){
                return ResponseEntity.ok().header("EntityClass", "E_ExpressSheet").body("快件运单状态错误!无法收件!");
            }
            nes.setAccepter(String.valueOf(uId));
            nes.setAccepteTime(getCurrentDate());
            nes.setStatus(ExpressSheet.STATUS.STATUS_RECEIVED);
            expressSheetService.save(nes);
            UserInfo userInfo = userInfoService.get(uId);
            TransPackageContent transPackageContent = new TransPackageContent();
            transPackageContent.setPackageId(userInfo.getReceivePackageId());
            transPackageContent.setExpressId(nes.getId());
            transPackageContent.setStatus(TransPackageContent.STATUS.STATUS_ACTIVE);
            transPackageContentService.save(transPackageContent);
            return ResponseEntity.ok().header("EntityClass", "ExpressSheet").body(nes);
        }
        catch(Exception e)
        {
            logger.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }


    /**
     * 派送快递
     * @param id 快递id
     * @param uId 派送人id
     * @return {@code ResponseEntity<ExpressSheet>, HttpStatus=200, Header={"EntityClass", "ExpressSheet"}}正确时返回快递单，
     * {@code ResponseEntity<String>, HttpStatus=500}异常时返回异常信息
     */
    @RequestMapping(value = "/dispatchExpressSheetId/id/{id}/uId/{uId}", method = RequestMethod.GET)
    public ResponseEntity dispatchExpressSheet(@PathVariable("id")String id, @PathVariable("uId")int uId) {
        TransPackageContent transPackageContent = new TransPackageContent();
        try{
            ExpressSheet nes = expressSheetService.get(id);
            nes.setDeliver(String.valueOf(uId));
            nes.setDeliveTime(getCurrentDate());
            nes.setStatus(ExpressSheet.STATUS.STATUS_DISPATCHED);
            expressSheetService.save(nes);
            transPackageContent.setPackageId(userInfoService.get(uId).getDelivePackageId());
            transPackageContent.setExpressId(nes.getId());
            transPackageContent.setStatus(TransPackageContent.STATUS.STATUS_ACTIVE);
            transPackageContentService.save(transPackageContent);
            return ResponseEntity.ok().header("EntityClass", "ExpressSheet").body(nes);
        }
        catch(Exception e)
        {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    /**
     * 交付快递
     * @param id 快递单id
     * @param uid 派送员id
     * @return {@code ResponseEntity<ExpressSheet>, HttpStatus=200, Header={"EntityClass", "ExpressSheet"}}正确时返回快递单，
     * {@code ResponseEntity<String>, HttpStatus=500}异常时返回异常信息
     */
    @RequestMapping(value = "/deliveryExpressSheetId/id/{id}/uid/{uid}", method = RequestMethod.GET)
    public ResponseEntity deliveryExpressSheetId(@PathVariable("id")String id, @PathVariable("uid")int uid) {
        TransPackageContent transPackageContent = new TransPackageContent();
        try{
            ExpressSheet nes = expressSheetService.get(id);
            nes.setStatus(ExpressSheet.STATUS.STATUS_DELIVERIED);
            expressSheetService.save(nes);
            transPackageContent = transPackageContentService.findByExpressIdAndStatus(id,
                    TransPackageContent.STATUS.STATUS_ACTIVE);
            transPackageContent.setStatus(TransPackageContent.STATUS.STATUS_OUTOF_PACKAGE);
            transPackageContentService.update(transPackageContent);

            return ResponseEntity.ok().header("EntityClass", "ExpressSheet").body(nes);
        }
        catch(Exception e)
        {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    /**
     * 得到包裹集合
     * @param property 属性
     * @param restrictions 限定条件 eq or like
     * @param value 属性值
     * @return 包裹集合
     */
    @RequestMapping(value = "/getTransPackageList/{Property}/{Restrictions}/{Value}", method = RequestMethod.GET)
    public List<TransPackage> getTransPackageList(@PathVariable("Property")String property, @PathVariable("Restrictions")String restrictions, @PathVariable("Value")String value) {
        List<TransPackage> list = new ArrayList<>();
        switch(restrictions.toLowerCase()){
            case "eq":
                list = transPackageService.findBy(property, value );
                break;
            case "like":
                list = transPackageService.findLike(property, value+"%");
                break;
                default:
        }
        return list;
    }

    /**
     * 得到包裹信息
     * @param id 包裹id
     * @return {@code ResponseEntity<TransPackage>, HttpStatus=200, Header={"EntityClass", "TransPackage"}}包裹单
     */
    @RequestMapping(value = "/getTransPackage/{id}", method = RequestMethod.GET)
    public ResponseEntity getTransPackage(@PathVariable("id")String id) {
        TransPackage es = transPackageService.get(id);
        return ResponseEntity.ok().header("EntityClass", "TransPackage")
                .body(es);
    }

    /**
     * 创建包裹
     * @param id 包裹单号
     * @param uid 使用者id
     * @return {@code ResponseEntity<TransPackage>, HttpStatus=200, Header={"EntityClass", "ExpressSheet"}}正确时返回包裹信息，
     * {@code ResponseEntity<String>, HttpStatus=500}异常时返回异常信息
     */
    @RequestMapping(value = "/newTransPackage", method = RequestMethod.POST)
    public ResponseEntity newTransPackage(@RequestBody String id, @RequestBody int uid){
        try{
            TransPackage npk = new TransPackage();
            npk.setId(id);
            npk.setCreateTime(getCurrentDate());
            transPackageService.save(npk);
            return ResponseEntity.ok().header("EntityClass", "TransPackage")
                    .body(npk);
        }
        catch(Exception e)
        {
            logger.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());

        }
    }



    /**
     * 打开包裹
     * @param uId 使用者id
     * @param id 包裹id
     * @return 正常情况返回0，传入包裹id不存在返回2，包裹处于新建状态返回1
     */
    @RequestMapping(value = "/openTransPackage/{uid}/{id}", method = RequestMethod.GET)
    public int openTransPackage(@PathVariable("uid")int uId,@PathVariable("id")String id) {
        List<TransPackageContent> transPackageContents = new ArrayList<>();
        UserInfo userInfo = new UserInfo();
        userInfo = userInfoService.get(uId);
        transPackageContents = transPackageContentService.findByPackageId(id);
        //传入包裹ID不存在 返回2
        if(transPackageContents.isEmpty()){
            return 2;
        }
        TransPackage transPackage = new TransPackage();
        transPackage = transPackageService.get(id);
        //包裹处于新建状态
        if(transPackage.getStatus() == 0){
            return 1;
        }
        transPackage.setStatus(0);
        transPackageService.update(transPackage);
        for (TransPackageContent transPackageContent : transPackageContents) {
            if (transPackageContent.getStatus() == 1) {
                continue;
            }
            transPackageContent.setStatus(TransPackageContent.STATUS.STATUS_OUTOF_PACKAGE);
            transPackageContentService.update(transPackageContent);
            ExpressSheet expressSheet = expressSheetService.get(transPackageContent.getExpressId());
            //设置快递为分件状态
            expressSheet.setStatus(ExpressSheet.STATUS.STATUS_PARTATION);
        }
        return 0;
    }

    /**
     * 确认包裹到达目的站点
     * @param transPackageId 转运包裹id
     * @param userId1 转运人员id
     * @param userId2 扫描员id
     * @return {@code Body="Success", HttpStatus=200} 成功消息, {@code Body="Unavaliable packageID", HttpStatus=500} 失败消息
     */
    @RequestMapping(value = "/deliveryTransPackage/{transPackageId}/{userId1}/{userId2}", method = RequestMethod.POST)
    public ResponseEntity<String> deliveryTransPackage(@PathVariable("transPackageId") String transPackageId,
                                               @PathVariable("userId1")int userId1,
                                               @PathVariable("userId2") int userId2) {
        UsersPackage usersPackage = new UsersPackage();
        TransHistory transHistory = new TransHistory();
        TransPackage transPackage = new TransPackage();
        UserInfo userInfo = new UserInfo();
        List<UsersPackage> usersPackages = new ArrayList<>();
        usersPackages = userPackageService.findBy("packageID", transPackageId, "SN", true);
        if (usersPackages.isEmpty()) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Unavailable packageID");
        }else{
            userInfo = userInfoService.get(userId2);
            userInfo.setTransPackageId(transPackageId);
            userInfoService.update(userInfo);
            usersPackage = usersPackages.get(0);
            userPackageService.remove(usersPackage.getSn());
            usersPackage.setUserUid(userInfo.getuId());
            usersPackage.setSn(0);
            userPackageService.save(usersPackage);
            transHistory.setActTime(getCurrentDate());
            transHistory.setuIdFrom(userId1);
            transHistory.setuIdTo(userId2);
            transPackage = transPackageService.get(transPackageId);
            transHistory.setPkg(transPackage);
            transHistoryService.save(transHistory);
            return ResponseEntity.ok("Success");
        }
    }

    /**
     * 把快递装进包裹中
     * @param transPackageId 包裹id
     * @param expressSheetId 快递id
     * @return {@code ResponseEntity<TransPackageContent>, HttpStatus=200, Header={"EntityClass", "TransPackageContent"}}正确时返回包裹内容，{@code ResponseEntity<String>, HttpStatus=500}失败时返回错误信息
     */
    @RequestMapping(value = "/packTransPackage/{transPackageId}/{expressSheetId}", method = RequestMethod.GET)
    public ResponseEntity packTransPackage(@PathVariable("transPackageId")String transPackageId, @PathVariable("expressSheetId")String expressSheetId) {
        try{
            TransPackageContent transPackageContent = new TransPackageContent();
            transPackageContent.setPackageId(transPackageId);
            transPackageContent.setExpressId(expressSheetId);
            transPackageContent.setStatus(TransPackageContent.STATUS.STATUS_ACTIVE);
            transPackageContentService.save(transPackageContent);
            return ResponseEntity.ok().header("EntityClass", "TransPackageContent")
                    .body(transPackageContent);
        }catch(Exception e)
        {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }


    /**
     * 保存包裹信息
     * @param obj 包裹
     * @return {@code ResponseEntity<TransPackage>, HttpStatus=200, Header={"EntityClass", "E_ExpressSheet"}}成功时返回包裹信息，{@code ResponseEntity<String>,HttpStatus=500}失败时返回失败信息
     */
    @RequestMapping(value = "/saveTransPackage", method = RequestMethod.POST)
    public ResponseEntity saveTransPackage(@RequestBody TransPackage obj){
        try {
            transPackageService.save(obj);
            return ResponseEntity.ok().header("EntityClass", "E_ExpressSheet")
                    .body(obj);

        }catch(Exception e)
        {
            logger.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }



    /**
     * 根据用户id得到与之相关联的包裹信息
     * @param userUId 用户id
     * @return 包裹信息集合
     */
    @RequestMapping(value = "/getUserPackage/{userUId}", method = RequestMethod.GET)
    public ResponseEntity<List<TransPackage>> getUsersPackage(@PathVariable("userUId")int userUId) {
        List<UsersPackage> usersPackageList = new ArrayList<>();
        usersPackageList = userPackageService.getTransPackageList(userUId);
        List<TransPackage> transPackageList  = new ArrayList<>();
        for (UsersPackage usersPackage : usersPackageList) {
            TransPackage transPackage = transPackageService.get(usersPackage.getPackageId());
            transPackageList.add(transPackage);
        }
        return ResponseEntity.ok(transPackageList);
    }
}
