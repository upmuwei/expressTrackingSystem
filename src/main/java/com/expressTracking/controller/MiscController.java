package com.expressTracking.controller;

import com.expressTracking.entity.*;
import com.expressTracking.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

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

	private final TransPackageService transPackageService;

	@Autowired
	public MiscController(TransNodeService transNodeService, CustomerInfoService customerInfoService,
						  RegionService regionService, PackageRouteService packageRouteService,
						  TransHistoryService transHistoryService, UserPackageService userPackageService,
						  TransPackageService transPackageService) {
		this.transNodeService = transNodeService;
		this.customerInfoService = customerInfoService;
		this.regionService = regionService;
		this.packageRouteService = packageRouteService;
		this.transHistoryService = transHistoryService;
		this.userPackageService = userPackageService;
		this.transPackageService = transPackageService;
	}

	/**
	 * 获取当前时间
	 * @return Date
	 */
	private Date getCurrentDate() throws Exception {

		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
		Date tm;
		try {
			tm= sdf.parse(sdf.format(new Date()));
		} catch (java.text.ParseException e) {
			e.printStackTrace();
			throw new Exception("获取时间出错");
		}
		return tm;
	}

	/**
	 * 获得节点信息
	 * @param id 节点id
	 * @return {@code HttpStatus=200, Header={"Type", "Select"}}节点信息
	 */
	@RequestMapping(value = "/getNode/{ID}",method = RequestMethod.GET)
	public ResponseEntity<TransNode> getNode(@PathVariable("ID") String id) {
		TransNode transNode = transNodeService.get(id);
		return ResponseEntity.ok().header("Type", "Select").body(transNode);
	}

	/**
	 * 获得节点信息
	 * @param regionCode 地区编码
	 * @param type 节点类型
	 * @return {@code HttpStatus=200, Header={"Type", "Select"}}节点信息集合
	 */
    @RequestMapping(value = "/getNodesList/{RegionCode}/{Type}",method = RequestMethod.GET)
	public ResponseEntity<List<TransNode>> getNodesList(@PathVariable("RegionCode") String regionCode, @PathVariable("Type") int type) {
		List<TransNode> transNodeList = transNodeService.findByRegionCodeAndType(regionCode, type);
    	return ResponseEntity.ok().header("Type", "Select").body(transNodeList);
	}

    /**
     * 保存节点信息
     * @param transNode 节点信息
     * @return {@code HttpStatus=200, Header={"Type", "Save"}}节点信息
     */
	@RequestMapping(value = "/saveNode",method = RequestMethod.POST)
	public ResponseEntity<TransNode> saveNodesList(@RequestBody TransNode transNode) {
		transNodeService.save(transNode);
		return ResponseEntity.ok().header("Type", "Save").body(transNode);

	}


	/**
	 * 获得客户信息集合
	 * @param name 姓名
	 * @return {@code HttpStatus=200, Header={"Type", "Select"}}用户信息集合
	 */
    @RequestMapping(value = "/getCustomerListByName/{name}",method = RequestMethod.GET)
	public ResponseEntity<List<CustomerInfo>> getCustomerListByName(@PathVariable("name") String name) {
		List<CustomerInfo> customerInfoList = customerInfoService.findByName(name);
		System.out.println("name="+name);
    	return ResponseEntity.ok().header("Type", "Select").body(customerInfoList);
	}

	/**
	 * 获得客户信息集合
	 * @param telCode 电话号码
	 * @return {@code HttpStatus=200, Header={"Type", "Select"}}用户信息集合
	 */
    @RequestMapping(value = "/getCustomerListByTelCode/{telCode}",method = RequestMethod.GET)
	public ResponseEntity<List<CustomerInfo>> getCustomerListByTelCode(@PathVariable("telCode") String telCode) {
		List<CustomerInfo> customerInfoList = customerInfoService.findByTelCode(telCode);
		return ResponseEntity.ok().header("Type", "Select").body(customerInfoList);
	}

	/**
	 * 获得客户信息
	 * @param id 用户id
	 * @return {@code HttpStatus=200, Header={"Type", "Select"}}用户信息
	 */
    @RequestMapping(value = "/getCustomerInfo/{id}",method = RequestMethod.GET)
	public ResponseEntity<CustomerInfo> getCustomerInfo(@PathVariable("id") int id) {
		CustomerInfo customerInfo = customerInfoService.get(id);
		return ResponseEntity.ok().header("Type", "Select").body(customerInfo);
	}

	/**
	 * 更新客户信息
	 * @param customerInfo 客户信息
	 * @return {@code HttpStatus=200, Header={"Type", "Update"}}用户信息
	 */
	@RequestMapping(value = "/updateCustomerInfo", method = RequestMethod.POST)
	public ResponseEntity<CustomerInfo> updateCustomerInfo(@RequestBody CustomerInfo customerInfo) {
		customerInfoService.update(customerInfo);
		return ResponseEntity.ok().header("Type", "Update").body(customerInfo);
	}

	/**
	 * 删除客户信息
	 * @param id 用户id
	 * @return {@code HttpStatus=200, Header={"Type", "Delete"}} 删除成功消息
	 */
    @RequestMapping(value = "/deleteCustomerInfo/{id}",method = RequestMethod.GET)
	public ResponseEntity<String> deleteCustomerInfo(@PathVariable("id") int id) {
		//customerInfoService.removeById(id);
		return ResponseEntity.ok().header("Type", "Delete")
				.contentType(MediaType.APPLICATION_JSON_UTF8)
				.body("{\"message\":\"删除成功\"}");
	}

	/**
	 * 保存客户信息
	 * @param obj 用户信息
	 * @return {@code HttpStatus=200, Header={"Type", "Save"}}用户信息
	 * @see CustomerInfo
	 */
    @RequestMapping(value = "/saveCustomerInfo",method = RequestMethod.POST)
	public ResponseEntity<CustomerInfo> saveCustomerInfo(@RequestBody CustomerInfo obj) {
    	customerInfoService.save(obj);
    	return ResponseEntity.ok().header("Type", "Save").body(obj);
	}


	/**
	 * 获得省份信息
	 * @return {@code HttpStatus=200, Header={"Type", "Select"}}省份信息
	 * @see CodeNamePair
	 */
    @RequestMapping(value = "/getProvinceList",method = RequestMethod.GET)
	public ResponseEntity<List<CodeNamePair>> getProvinceList() {
    	List<Region> regionList = regionService.getProvinceList();
		List<CodeNamePair> listCN = new ArrayList<>();
		for(Region rg : regionList){
			CodeNamePair cn = new CodeNamePair(rg.getRegionCode(),rg.getPrv());
			listCN.add(cn);
		}
		System.out.println("测试code和name"+listCN.get(0).getCode()+listCN.get(0).getName());
		return ResponseEntity.ok().header("Type", "Select").body(listCN);
	}

	/**
	 * 获得城市信息
	 * @param prv 省份名称
	 * @return {@code HttpStatus=200, Header={"Type", "Select"}}城市信息
	 * @see CodeNamePair
	 */
    @RequestMapping(value = "/getCityList/{prv}",method = RequestMethod.GET)
	public ResponseEntity<List<CodeNamePair>> getCityList(@PathVariable("prv") String prv) {
		List<Region> regionList = regionService.getCityList(prv);
		List<CodeNamePair> listCN = new ArrayList<>();
		for(Region rg : regionList){
			CodeNamePair cn = new CodeNamePair(rg.getRegionCode(),rg.getCty());
			listCN.add(cn);
		}
		return ResponseEntity.ok().header("Type", "Select").body(listCN);
	}

	/**
	 * 获得乡镇信息
	 * @param city 城市名称
	 * @return {@code HttpStatus=200, Header={"Type", "Select"}}乡镇信息
	 * @see CodeNamePair
	 */
    @RequestMapping(value = "/getTownList/{city}",method = RequestMethod.GET)
	public ResponseEntity<List<CodeNamePair>> getTownList(@PathVariable("city") String city) {
		List<Region> regionList = regionService.getTownList(city);
		List<CodeNamePair> listCN = new ArrayList<>();
		for(Region rg : regionList){
			CodeNamePair cn = new CodeNamePair(rg.getRegionCode(),rg.getTwn());
			listCN.add(cn);
		}
		return ResponseEntity.ok().header("Type", "Select").body(listCN);
	}

	/**
	 * 获得地区全名称（省+市+乡镇）
	 * @param code 地区编码
	 * @return {@code HttpStatus=200, Header={"Type", "Select"}}地区全名称
	 */
    @RequestMapping(value = "/getRegionString/{id}",method = RequestMethod.GET)
	public ResponseEntity<String> getRegionString(@PathVariable("id") String code) {
		String regionName =  regionService.getRegionNameById(code);
		return ResponseEntity.ok().header("Type", "Select").body(regionName);
	}

	/**
	 * 获取地区信息
	 * @param code 地区编码
	 * @return {@code HttpStatus=200, Header={"Type", "Select"}}地区信息
	 * @see Region
	 */
    @RequestMapping(value = "/getRegion/{id}",method = RequestMethod.GET)
	public ResponseEntity<Region> getRegion(@PathVariable("id") String code) {
    	Region region = regionService.getFullNameRegionById(code);
		return ResponseEntity.ok().header("Type", "Select").body(region);
    }


	/**
	 * 保存包裹路线信息
	 * @param obj 包裹路线信息
	 * @return {@code HttpStatus=200, Header={"Type", "Save"}}包裹路线信息
	 */
	@RequestMapping(value = "/uploadRoute" , method = RequestMethod.POST)
	public ResponseEntity<PackageRoute> savePackageRoute(@RequestBody PackageRoute obj) throws Exception {
		obj.setTm(getCurrentDate());
		packageRouteService.save(obj);
		return ResponseEntity.ok().header("Type", "Save").body(obj);

	}

	/**
	 * 得到包裹路线信息
	 * @param expressSheetId 快递单号
	 * @return {@code HttpStatus=200, Header={"Type", "Select"}}包裹路线信息
	 * @see PackageRoute
	 */
	@RequestMapping(value = "/getPackageRoute/{expressSheetId}" , method = RequestMethod.GET)
	public ResponseEntity<List<PackageRoute>> getPackageRouteList(@PathVariable("expressSheetId") String expressSheetId) {
		List<PackageRoute> packageRoutes = packageRouteService.getPackageRouteList(expressSheetId);
		return ResponseEntity.ok().header("Type", "Select").body(packageRoutes);
	}

	/**
	 * 为包裹指派转运员
	 * @param packageId 包裹id
	 * @param nodeUId 转运节点工作人员id
	 * @param userId 转运员id
	 * @return {@code HttpStatus=200, Header={"Type", "Update"}} 成功消息
	 */
	@RequestMapping(value = "/appointTransPorter/{packageId}/{nodeUId}/{userId}" , method = RequestMethod.GET)
	public ResponseEntity<String> appointTransPorter(@PathVariable("packageId")String packageId,
									 @PathVariable("nodeUId")int nodeUId,@PathVariable("userId")int userId) throws Exception {
        UsersPackage usersPackage = userPackageService.findByPackageId(packageId);
        TransPackage transPackage = transPackageService.get(packageId);
        if (usersPackage == null) {
            throw new Exception("包裹id不存在");
        }else if (transPackage.getStatus() == 2) {
			throw  new Exception("包裹处于转运状态，不能为其指派转运员");
        } else {
        	TransHistory transHistory = new TransHistory();
        	userPackageService.remove(usersPackage.getSn());
        	usersPackage.setUserUid(userId);
        	usersPackage.setSn(0);
        	userPackageService.save(usersPackage);
        	transHistory.setuIdFrom(nodeUId);
        	transHistory.setuIdTo(userId);
        	transHistory.setActTime(getCurrentDate());
        	transHistory.setPackageId(packageId);
        	transHistoryService.save(transHistory);
        	return ResponseEntity.ok().header("Type","Update")
					.contentType(MediaType.APPLICATION_JSON_UTF8)
					.body("{\"message\":\"Success\"}");
		}
	}

	/**
	 * 获得包裹历史记录
	 * @param expressSheetID 快递id
	 * @return {@code HttpStatus=200, Header={"Type", "Select"}}包裹历史记录集合
	 */
	@RequestMapping(value = "/getTransHistory/{expressSheetID}" , method = RequestMethod.GET)
	public ResponseEntity<List<TransHistory>> getTransHistory(@PathVariable("expressSheetID") String expressSheetID) {
		List<TransHistory> transHistoryList = transHistoryService.getTransHistory(expressSheetID);
		return ResponseEntity.ok().header("Type", "Select").body(transHistoryList);
	}
}
