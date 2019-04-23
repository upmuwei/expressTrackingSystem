package com.expressTracking.entity;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class UserInfo implements Serializable {

	private static final long serialVersionUID = 6899152987896840262L;

	public UserInfo() {
	}

	private int uId;

	private String password;

	private String name;

	private Integer uRull;

	private String telCode;

	/**
	 * 审核状态
	 * 未审核未0
	 * 审核通过为1
	 */
	private Integer status;

	/**
	 * 所属转运节点
	 */
	private String dptId;

	/**
	 * 揽收货篮id
	 */
	private String receivePackageId;

	/**
	 * 派送货篮id
	 */
	private String delivePackageId;


	private String transPackageId;

	private Set<UsersPackage> usersPackage = new HashSet<>();

	public int getuId() {
		return uId;
	}

	public void setuId(int uId) {
		this.uId = uId;
	}

	public int getORMID() {
		return getuId();
	}
	
	public void setPassword(String value) {
		this.password = value;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setName(String value) {
		this.name = value;
	}
	
	public String getName() {
		return name;
	}

	public Integer getuRull() {
		return uRull;
	}

	public void setuRull(Integer uRull) {
		this.uRull = uRull;
	}

	public void setTelCode(String value) {
		this.telCode = value;
	}
	
	public String getTelCode() {
		return telCode;
	}
	
	public void setStatus(Integer value) {
		this.status = value;
	}
	
	public Integer getStatus() {
		return status;
	}
	
	public void setDptId(String value) {
		this.dptId = value;
	}
	
	public String getDptId() {
		return dptId;
	}
	
	public void setReceivePackageId(String value) {
		this.receivePackageId = value;
	}
	
	public String getReceivePackageId() {
		return receivePackageId;
	}
	
	public void setDelivePackageId(String value) {
		this.delivePackageId = value;
	}
	
	public String getDelivePackageId() {
		return delivePackageId;
	}
	
	public void setTransPackageId(String value) {
		this.transPackageId = value;
	}
	
	public String getTransPackageId() {
		return transPackageId;
	}
	
	public void setUsersPackage(Set<UsersPackage> value) {
		this.usersPackage = value;
	}
	
	public Set<UsersPackage> getUsersPackage() {
		return usersPackage;
	}
	
	
	@Override
	public String toString() {
		return toString(false);
	}
	
	public String toString(boolean idOnly) {
		if (idOnly) {
			return String.valueOf(getuId());
		}
		else {
			return "UserInfo[ " +
					"UID=" + getuId() + " " +
					"PWD=" + getPassword() + " " +
					"Name=" + getName() + " " +
					"URull=" + getuRull() + " " +
					"TelCode=" + getTelCode() + " " +
					"Status=" + getStatus() + " " +
					"DptID=" + getDptId() + " " +
					"ReceivePackageID=" + getReceivePackageId() + " " +
					"DelivePackageID=" + getDelivePackageId() + " " +
					"TransPackageID=" + getTransPackageId() + " " +
					"UsersPackage.size=" + getUsersPackage().size() + " " +
					"]";
		}
	}
}
