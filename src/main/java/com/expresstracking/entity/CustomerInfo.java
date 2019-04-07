package com.expresstracking.entity;

import java.io.Serializable;

public class CustomerInfo implements Serializable {

	private static final long serialVersionUID = -3267943602377867497L;

	public CustomerInfo() {
	}

	private int id;

	private String name;

	/**
	 * 电话号码
	 */
	private String telCode;

	/**
	 * 单位
	 */
	private String department;

	/**
	 * 地区编码
	 */
	private String regionCode;

	/**
	 * 地址
	 */
	private String address;

	/**
	 * 邮政编码
	 */
	private int postCode;
	
	public void setId(int value) {
		this.id = value;
	}
	
	public int getId() {
		return id;
	}
	
	public int getORMID() {
		return getId();
	}
	
	public void setName(String value) {
		this.name = value;
	}
	
	public String getName() {
		return name;
	}
	
	public void setTelCode(String value) {
		this.telCode = value;
	}
	
	public String getTelCode() {
		return telCode;
	}
	
	public void setDepartment(String value) {
		this.department = value;
	}
	
	public String getDepartment() {
		return department;
	}
	
	public void setRegionCode(String value) {
		this.regionCode = value;
	}
	
	public String getRegionCode() {
		return regionCode;
	}
	
	public void setAddress(String value) {
		this.address = value;
	}
	
	public String getAddress() {
		return address;
	}
	
	public void setPostCode(int value) {
		this.postCode = value;
	}
	
	public int getPostCode() {
		return postCode;
	}

	@Override
	public String toString() {
		return toString(false);
	}
	
	public String toString(boolean idOnly) {
		if (idOnly) {
			return String.valueOf(getId());
		}
		else {
			return "CustomerInfo[ " +
					"ID=" + getId() + " " +
					"Name=" + getName() + " " +
					"TelCode=" + getTelCode() + " " +
					"Department=" + getDepartment() + " " +
					"RegionCode=" + getRegionCode() + " " +
					"Address=" + getAddress() + " " +
					"PostCode=" + getPostCode() + " " +
					"]";
		}
	}

	private String regionString;

	public void setRegionString(String value) {
		this.regionString = value;
	}
	
	public String getRegionString() {
		return regionString;
	}

}
