package com.expressTracking.entity;

import java.io.Serializable;
import java.util.*;


public class TransPackage implements Serializable {

	private static final long serialVersionUID = 3050390478904210174L;

	public TransPackage() {
	}

	public TransPackage(String id, String sourceNode, String targetNode, Date createTime, Integer status) {
		this.id = id;
		this.sourceNode = sourceNode;
		this.targetNode = targetNode;
		this.createTime = createTime;
		this.status = status;
	}

	/**
	 * 包裹开头字符必须为1
	 */
	private String id;

	/**
	 * 原节点
	 */
	private String sourceNode;

	/**
	 * 目的节点
	 */
	private String targetNode;

	/**
	 * 创建时间
	 */
	private Date createTime;

	/**
	 *  0,新建
	 * 	1,打包
	 * 	2,转运
	 * 	3,分拣(分拣货篮)
	 * 	4,揽收货篮
	 * 	5,派送货篮
	 */
	private Integer status;

	private List<PackageRoute> route = new ArrayList<>();

	private UsersPackage user;

	private List<TransPackageContent> content = new ArrayList<>();

	private List<TransHistory> history = new ArrayList<>();
	
	public void setId(String value) {
		this.id = value;
	}
	
	public String getId() {
		return id;
	}
	
	public String getORMId() {
		return getId();
	}
	
	public void setSourceNode(String value) {
		this.sourceNode = value;
	}
	
	public String getSourceNode() {
		return sourceNode;
	}
	
	public void setTargetNode(String value) {
		this.targetNode = value;
	}
	
	public String getTargetNode() {
		return targetNode;
	}
	
	public void setCreateTime(Date value) {
		this.createTime = value;
	}
	
	public Date getCreateTime() {
		return createTime;
	}
	
	public void setStatus(Integer value) {
		this.status = value;
	}
	
	public Integer getStatus() {
		return status;
	}
	
	public void setRoute(List<PackageRoute> value) {
		this.route = value;
	}
	
	public List<PackageRoute> getRoute() {
		return route;
	}

	public void setUser(UsersPackage value) {
		this.user = value;
	}
	
	public UsersPackage getUser() {
		return user;
	}
	
	public void setContent(List<TransPackageContent> value) {
		this.content = value;
	}
	
	public List<TransPackageContent> getContent() {
		return content;
	}
	
	
	public void setHistory(List<TransHistory> value) {
		this.history = value;
	}
	
	public List<TransHistory> getHistory() {
		return history;
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
			StringBuilder sb = new StringBuilder();
			sb.append("TransPackageService[ ");
			sb.append("ID=").append(getId()).append(" ");
			sb.append("SourceNode=").append(getSourceNode()).append(" ");
			sb.append("TargetNode=").append(getTargetNode()).append(" ");
			sb.append("CreateTime=").append(getCreateTime()).append(" ");
			sb.append("Status=").append(getStatus()).append(" ");
			sb.append("Route.size=").append(getRoute().size()).append(" ");
			if (getUser() != null) {
				sb.append("User.Persist_ID=").append(getUser().toString(true)).append(" ");
			}
			else {
				sb.append("User=null ");
				sb.append("Content.size=").append(getContent().size()).append(" ");
				sb.append("History.size=").append(getHistory().size()).append(" ");
				sb.append("]");
			}
			return sb.toString();
		}
	}
}
