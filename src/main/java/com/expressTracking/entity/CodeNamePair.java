package com.expressTracking.entity;

/**
 * @author muwei
 * @date 2019/4/5
 */
public class CodeNamePair {

	/**
	 * 地区编码
	 */
	private String code;

	/**
	 * 省、市或者乡镇名称
	 */
	private String name;
	
	public CodeNamePair(String code, String name){
		this.code = code;
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}