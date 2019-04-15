package com.expressTracking.entity;

import java.io.Serializable;

public class Region implements Serializable {

	private static final long serialVersionUID = 2531774702426085013L;

	public Region() {
	}

	/**
	 * 地区编码
	 */
	private String regionCode;

	/**
	 * 省
	 */
	private String prv;

	/**
	 * 市
	 */
	private String cty;

	/**
	 * 镇
	 */
	private String twn;

	private int stage;
	
	public void setRegionCode(String value) {
		this.regionCode = value;
	}
	
	public String getRegionCode() {
		return regionCode;
	}
	
	public void setPrv(String value) {
		this.prv = value;
	}
	
	public String getPrv() {
		return prv;
	}
	
	public void setCty(String value) {
		this.cty = value;
	}
	
	public String getCty() {
		return cty;
	}
	
	public void setTwn(String value) {
		this.twn = value;
	}
	
	public String getTwn() {
		return twn;
	}
	
	public void setStage(int value) {
		this.stage = value;
	}
	
	public int getStage() {
		return stage;
	}

	@Override
	public String toString() {
		return toString(false);
	}
	
	public String toString(boolean idOnly) {
		if (idOnly) {
			return String.valueOf(getRegionCode());
		}
		else {
			return "Region[ " +
					"RegionCode=" + getRegionCode() + " " +
					"Prv=" + getPrv() + " " +
					"Cty=" + getCty() + " " +
					"Twn=" + getTwn() + " " +
					"Stage=" + getStage() + " " +
					"]";
		}
	}

}
