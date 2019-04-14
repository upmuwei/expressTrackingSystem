package com.expressTracking.controller;

import com.expressTracking.entity.*;
import com.expressTracking.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author 19231
 * @date 2019/4/5
 */
@RestController
@RequestMapping("/misc")
public class MiscController {

	private final TransNodeService transNodeService;

	private final CustomerInfoService customerInfoService;

	private final RegionService regionService;

	private final PackageRouteService packageRouteService;

	private final TransHistoryService transHistoryService;

	private final UserPackageService userPackageService;

	private final TransPackageContentService transPackageContentService;

	private final UserInfoService userInfoService;

	private final ExpressSheetService expressSheetService;

	private final TransPackageService transPackageService;

	@Autowired
	public MiscController(TransNodeService transNodeService, CustomerInfoService customerInfoService,
						  RegionService regionService, PackageRouteService packageRouteService,
						  TransHistoryService transHistoryService, UserPackageService userPackageService,
						  TransPackageContentService transPackageContentService, UserInfoService userInfoService,
						  ExpressSheetService expressSheetService, TransPackageService transPackageService) {
		this.transNodeService = transNodeService;
		this.customerInfoService = customerInfoService;
		this.regionService = regionService;
		this.packageRouteService = packageRouteService;
		this.transHistoryService = transHistoryService;
		this.userPackageService = userPackageService;
		this.transPackageContentService = transPackageContentService;
		this.userInfoService = userInfoService;
		this.expressSheetService = expressSheetService;
		this.transPackageService = transPackageService;
	}

	/**
	 * 获得节点信息
	 * @param id 节点id
	 * @return {@code ResponseEntity<TransNode>, HttpStatus=200, Header={"EntityClass", "TransNode"}}返回节点信息
	 */
	@RequestMapping(value = "/getNode/{ID}",method = RequestMethod.GET)
	public ResponseEntity<TransNode> getNode(@PathVariable("ID") String id) {
		TransNode transNode = transNodeService.get(id);
		return ResponseEntity.ok().header("EntityClass", "TransNode").body(transNode);
	}

	/**
	 * 获得节点信息
	 * @param regionCode 地区编码
	 * @param type 节点类型
	 * @return 节点信息集合
	 */
    @RequestMapping(value = "/getNodesList/{RegionCode}/{Type}",method = RequestMethod.GET)
	public List<TransNode> getNodesList(@PathVariable("RegionCode") String regionCode, @PathVariable("Type") int type) {
		return transNodeService.findByRegionCodeAndType(regionCode, type);
	}

    /**
     * 保存节点信息
     * @param transNode 节点信息
     * @return {@code ResponseEntity<TransNode>, HttpStatus=200, Header={"EntityClass", "R_TransNode"}}正确时返回节点信息，
     * {@code ResponseEntity<String>, HttpStatus=500} 异常时返回异常信息
     */
	@RequestMapping(value = "/saveNode",method = RequestMethod.POST)
	public ResponseEntity saveNodesList(@RequestBody TransNode transNode) {
		try{
			transNodeService.save(transNode);
			return ResponseEntity.ok().header("EntityClass", "R_TransNode").body(transNode);
		}
		catch(Exception e)
		{
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
	}


	/**
	 * 获得客户信息集合
	 * @param name 姓名
	 * @return 用户信息集合
	 */
    @RequestMapping(value = "/getCustomerListByName/{name}",method = RequestMethod.GET)
	public List<CustomerInfo> getCustomerListByName(@PathVariable("name") String name) {
		return customerInfoService.findByName(name);
	}

	/**
	 * 获得客户信息集合
	 * @param telCode 电话号码
	 * @return 用户信息集合
	 */
    @RequestMapping(value = "/getCustomerListByTelCode/{telCode}",method = RequestMethod.GET)
	public List<CustomerInfo> getCustomerListByTelCode(@PathVariable("TelCode") String telCode) {
		return customerInfoService.findByTelCode(telCode);
	}

	/**
	 * 获得客户信息
	 * @param id 用户id
	 * @return {@code HttpStatus=200, Header={"EntityClass", "CustomerInfo"}}用户信息
	 */
    @RequestMapping(value = "/getCustomerInfo/{id}",method = RequestMethod.GET)
	public ResponseEntity<CustomerInfo> getCustomerInfo(@PathVariable("id") int id) {
		CustomerInfo cstm = customerInfoService.get(id);
		return ResponseEntity.ok().header("EntityClass", "CustomerInfo").body(cstm);
	}

	/**
	 * 更新客户信息
	 * @param customerInfo 客户信息
	 * @return {@code ResponseEntity<CustomerInfo>, HttpStatus=200, Header={"EntityClass", "R_ExpressSheet"}}正确时用户信息，
	 * {@code ResponseEntity<String>, HttpStatus=500} 异常时返回异常信息
	 */
	@RequestMapping(value = "/updateCustomerInfo", method = RequestMethod.POST)
	public ResponseEntity updateCustomerInfo(@RequestBody CustomerInfo customerInfo) {
		try{
			customerInfoService.update(customerInfo);
			return ResponseEntity.ok().header("EntityClass", "R_ExpressSheet").body(customerInfo);
		}
		catch(Exception e)
		{
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
	}

	/**
	 * 删除客户信息
	 * @param id 用户id
	 * @return {@code HttpStatus=200, Header={"EntityClass", "D_CustomerInfo"}} 删除成功消息
	 */
    @RequestMapping(value = "/deleteCustomerInfo/{id}",method = RequestMethod.GET)
	public ResponseEntity<String> deleteCustomerInfo(@PathVariable("id") int id) {
		customerInfoService.removeById(id);
		return ResponseEntity.ok().header("EntityClass", "D_CustomerInfo").body("Deleted");
	}

	/**
	 * 保存客户信息
	 * @param obj 用户信息
	 * @return {@code ResponseEntity<CustomerInfo>, HttpStatus=200, Header={"EntityClass", "R_CustomerInfo"}}正确时用户信息，
	 * {@code ResponseEntity<String>, HttpStatus=500} 异常时返回异常信息
	 * @see CustomerInfo
	 */
    @RequestMapping(value = "/saveCustomerInfo",method = RequestMethod.POST)
	public ResponseEntity saveCustomerInfo(@RequestBody CustomerInfo obj) {
		try{
			customerInfoService.save(obj);
			return ResponseEntity.ok().header("EntityClass", "R_CustomerInfo").body(obj);
		}
		catch(Exception e)
		{
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
	}


	/**
	 * 获得省份信息
	 * @return 省份信息
	 * @see CodeNamePair
	 */
    @RequestMapping(value = "/getProvinceList",method = RequestMethod.GET)
	public List<CodeNamePair> getProvinceList() {
			List<Region> listrg = regionService.getProvinceList();
		List<CodeNamePair> listCN = new ArrayList<>();
		for(Region rg : listrg){
			CodeNamePair cn = new CodeNamePair(rg.getORMID(),rg.getPrv());
			listCN.add(cn);
		}
		return listCN;
	}

	/**
	 * 获得城市信息
	 * @param prv 省份名称
	 * @return 城市信息
	 * @see CodeNamePair
	 */
    @RequestMapping(value = "/getCityList/{prv}",method = RequestMethod.GET)
	public List<CodeNamePair> getCityList(@PathVariable("prv") String prv) {
		List<Region> listrg = regionService.getCityList(prv);
		List<CodeNamePair> listCN = new ArrayList<>();
		for(Region rg : listrg){
			CodeNamePair cn = new CodeNamePair(rg.getORMID(),rg.getCty());
			listCN.add(cn);
		}
		return listCN;
	}

	/**
	 * 获得乡镇信息
	 * @param city 城市名称
	 * @return 乡镇信息
	 * @see CodeNamePair
	 */
    @RequestMapping(value = "/getTownList/{city}",method = RequestMethod.GET)
	public List<CodeNamePair> getTownList(@PathVariable("city") String city) {
		List<Region> listrg = regionService.getTownList(city);
		List<CodeNamePair> listCN = new ArrayList<>();
		for(Region rg : listrg){
			CodeNamePair cn = new CodeNamePair(rg.getORMID(),rg.getTwn());
			listCN.add(cn);
		}
		return listCN;
	}

	/**
	 * 获得地区全名称（省+市+乡镇）
	 * @param code 地区编码
	 * @return 地区全名称
	 */
    @RequestMapping(value = "/getRegionString/{id}",method = RequestMethod.GET)
	public String getRegionString(@PathVariable("id") String code) {
		return regionService.getRegionNameById(code);
	}

	/**
	 * 获取地区信息
	 * @param code 地区编码
	 * @return 地区信息
	 * @see Region
	 */
    @RequestMapping(value = "/getRegion/{id}",method = RequestMethod.GET)
	public Region getRegion(@PathVariable("id") String code) {
		return regionService.getFullNameRegionById(code);
	}


	/**
	 * 保存包裹路线信息
	 * @param obj 包裹路线信息
	 * @return {@code ResponseEntity<PackageRoute>, HttpStatus=200, Header={"EntityClass", "PackageRoute"}}正确时返回包裹路线信息,
	 * {@code ResponseEntity<String>, HttpStatus=500} 异常时返回异常信息
	 */
	@RequestMapping(value = "/uploadRoute" , method = RequestMethod.POST)
	public ResponseEntity savePackageRoute(@RequestBody PackageRoute obj) {
		try{
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
			Date date = sdf.parse(sdf.format(new Date()));
			obj.setTm(date);
			System.out.println(obj);
			packageRouteService.save(obj);
			return ResponseEntity.ok().header("EntityClass", "PackageRoute").body(obj);
		}catch(Exception e){
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
	}

	/**
	 * 得到包裹路线信息
	 * @param expressSheetId 快递单号
	 * @return 包裹路线信息
	 * @see PackageRoute
	 */
	@RequestMapping(value = "/getPackageRoute/{expressSheetId}" , method = RequestMethod.GET)
	public List<PackageRoute> getPackageRouteList(@PathVariable("expressSheetId") String expressSheetId) {
		List<PackageRoute> packageRoutes = new ArrayList<>();
		packageRoutes = packageRouteService.getPackageRouteList(expressSheetId);
		return packageRoutes;
	}

	/**
	 * 为包裹指派转运员
	 * @param packageId 包裹id
	 * @param nodeUId 转运节点工作人员id
	 * @param userId 转运员id
	 * @return {@code "Success"} 成功消息, {@code "Unavaliable packageID"} 失败消息
	 */
	@RequestMapping(value = "/appointTransPorter/{packageId}/{nodeUId}/{userId}" , method = RequestMethod.GET)
	public String appointTransPorter(@PathVariable("packageId")String packageId,@PathVariable("nodeUId")int nodeUId,@PathVariable("userId")int userId) {
		UsersPackage usersPackage = new UsersPackage();
		TransHistory transHistory = new TransHistory();
		TransPackage transPackage = new TransPackage();
		List<TransPackageContent> transPackageContents = new ArrayList<>();
		UserInfo userInfo = new UserInfo();
		List<UsersPackage> usersPackages = new ArrayList<>();
		usersPackages = userPackageService.findBy("packageID", packageId);
		transPackageContents = transPackageContentService.findByPackageIdAndStatus(packageId, TransPackageContent.STATUS.STATUS_ACTIVE);
		if (usersPackages.isEmpty()) {
			return "Unavailable packageID";
		}else{
			for (TransPackageContent transPackageContent : transPackageContents) {
				ExpressSheet expressSheet = new ExpressSheet();
				expressSheet = expressSheetService.get(transPackageContent.getExpressId());
				expressSheet.setStatus(ExpressSheet.STATUS.STATUS_TRANSPORT);
				expressSheetService.update(expressSheet);
			}
			userInfo = userInfoService.get(userId);
			userInfo.setTransPackageId(packageId);
			userInfoService.update(userInfo);
			usersPackage = usersPackages.get(0);
			userPackageService.remove(usersPackage.getSn());
			usersPackage.setUserUid(userInfo.getuId());
			usersPackage.setSn(0);
			userPackageService.save(usersPackage);
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
			Date date;
			try {
				date = sdf.parse(sdf.format(new Date()));
				transHistory.setActTime(date);
				transHistory.setuIdFrom(nodeUId);
				transHistory.setuIdTo(userId);
				transPackage = transPackageService.get(packageId);
				transHistory.setPkg(transPackage);
				transHistoryService.save(transHistory);
			} catch (ParseException e) {

				e.printStackTrace();
			}
			return "Success";
		}
	}


	/**
	 * 获得包裹历史记录
	 * @param expressSheetID 快递id
	 * @return 包裹历史记录集合
	 */
	@RequestMapping(value = "/getTransHistory/{expressSheetID}" , method = RequestMethod.GET)
	public List<TransHistory> getTransHistory(@PathVariable("expressSheetID") String expressSheetID) {
		return transHistoryService.getTransHistory(expressSheetID);
	}

}
