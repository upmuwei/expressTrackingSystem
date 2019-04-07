package com.expresstracking.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;


public class TransPackage implements Serializable {

	private static final long serialVersionUID = 3050390478904210174L;

	public TransPackage() {
	}

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

	private Set<PackageRoute> route = new HashSet<>();

	private UsersPackage user;

	private Set<TransPackageContent> content = new HashSet<>();

	private Set<TransHistory> history = new HashSet<>();
	
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
	
	public void setRoute(java.util.Set<PackageRoute> value) {
		this.route = value;
	}
	
	public java.util.Set<PackageRoute> getRoute() {
		return route;
	}
	
	
	public void setUser(UsersPackage value) {
		this.user = value;
	}
	
	public UsersPackage getUser() {
		return user;
	}
	
	public void setContent(Set<TransPackageContent> value) {
		this.content = value;
	}
	
	public Set<TransPackageContent> getContent() {
		return content;
	}
	
	
	public void setHistory(Set<TransHistory> value) {
		this.history = value;
	}
	
	public Set<TransHistory> getHistory() {
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
