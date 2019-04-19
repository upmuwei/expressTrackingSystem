package com.expressTracking.entity;

import org.springframework.lang.NonNull;

import java.io.Serializable;

/**
 * 转运节点
 */
public class TransNode implements Serializable {

	private static final long serialVersionUID = -3696487402698365947L;

	public TransNode() {
	}

	@NonNull
	private String id;

	@NonNull
	private String nodeName;

	@NonNull
	private Integer nodeType;

	@NonNull
	private String regionCode;

	@NonNull
	private String telCode;

	@NonNull
	private Float x;

	@NonNull
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
