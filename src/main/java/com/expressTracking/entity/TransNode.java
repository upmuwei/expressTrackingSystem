package com.expressTracking.entity;

import java.io.Serializable;

/**
 * 转运节点
 */
public class TransNode implements Serializable {

	private static final long serialVersionUID = -3696487402698365947L;

	public TransNode() {
	}

	private String id;

	private String nodeName;

	private Integer nodeType;

	private String regionCode;

	private String telCode;

	private Float x;

	private Float y;
	
	public void setId(String value) {
		this.id = value;
	}
	
	public String getId() {
		return id;
	}
	
	public String getORMId() {
		return getId();
	}
	
	public void setNodeName(String value) {
		this.nodeName = value;
	}
	
	public String getNodeName() {
		return nodeName;
	}
	
	public void setNodeType(Integer value) {
		this.nodeType = value;
	}
	
	public Integer getNodeType() {
		return nodeType;
	}
	
	public void setRegionCode(String value) {
		this.regionCode = value;
	}
	
	public String getRegionCode() {
		return regionCode;
	}
	
	public void setTelCode(String value) {
		this.telCode = value;
	}
	
	public String getTelCode() {
		return telCode;
	}
	
	public void setX(Float value) {
		this.x = value;
	}
	
	public Float getX() {
		return x;
	}
	
	public void setY(Float value) {
		this.y = value;
	}
	
	public Float getY() {
		return y;
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
			return "TransNode[ " +
					"ID=" + getId() + " " +
					"NodeName=" + getNodeName() + " " +
					"NodeType=" + getNodeType() + " " +
					"RegionCode=" + getRegionCode() + " " +
					"TelCode=" + getTelCode() + " " +
					"X=" + getX() + " " +
					"Y=" + getY() + " " +
					"]";
		}
	}

}
