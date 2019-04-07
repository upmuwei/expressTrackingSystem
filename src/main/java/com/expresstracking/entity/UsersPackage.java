package com.expresstracking.entity;


import java.io.Serializable;

public class UsersPackage implements Serializable {

	private static final long serialVersionUID = -6753829022427770282L;

	public UsersPackage() {
	}
	
	

	private int sn;
	

	private UserInfo userU;
	

	private TransPackage pkg;
	
	public void setSN(int value) {
		this.sn = value;
	}
	
	public int getSN() {
		return sn;
	}
	
	public int getORMID() {
		return getSN();
	}
	
	public void setUserU(UserInfo value) {
		this.userU = value;
	}
	
	public UserInfo getUserU() {
		return userU;
	}
	
	public void setPkg(TransPackage value) {
		this.pkg = value;
	}
	
	public TransPackage getPkg() {
		return pkg;
	}
	
	@Override
	public String toString() {
		return toString(false);
	}
	
	public String toString(boolean idOnly) {
		if (idOnly) {
			return String.valueOf(getSN());
		}
		else {
			StringBuilder sb = new StringBuilder();
			sb.append("UsersPackage[ ");
			sb.append("SN=").append(getSN()).append(" ");
			if (getUserU() != null) {
				sb.append("UserU.Persist_ID=").append(getUserU().toString(true)).append(" ");
			}
			else {
				sb.append("UserU=null ");
			}
			if (getPkg() != null) {
				sb.append("Pkg.Persist_ID=").append(getPkg().toString(true)).append(" ");
			}
			else {
				sb.append("Pkg=null ");
			}
			sb.append("]");
			return sb.toString();
		}
	}
	
}
