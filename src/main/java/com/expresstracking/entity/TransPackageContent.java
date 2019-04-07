package com.expresstracking.entity;


import java.io.Serializable;


public class TransPackageContent implements Serializable {

	private static final long serialVersionUID = -2696910600791838998L;

	public TransPackageContent() {
	}

	private int sn;

	private ExpressSheet express;

	private TransPackage pkg;

	private int status;

	public void setSn(int value) {
		this.sn = value;
	}
	
	public int getSn() {
		return sn;
	}
	
	public int getORMId() {
		return getSn();
	}
	
	public void setExpress(ExpressSheet value) {
		this.express = value;
	}
	
	public ExpressSheet getExpress() {
		return express;
	}
	
	public void setPkg(TransPackage value) {
		this.pkg = value;
	}
	
	public TransPackage getPkg() {
		return pkg;
	}
	
	public void setStatus(int value) {
		this.status = value;
	}
	
	public int getStatus() {
		return status;
	}
	
	@Override
	public String toString() {
		return toString(false);
	}
	
	public String toString(boolean idOnly) {
		if (idOnly) {
			return String.valueOf(getSn());
		}
		else {
			StringBuilder sb = new StringBuilder();
			sb.append("TransPackageContent[ ");
			sb.append("SN=").append(getSn()).append(" ");
			if (getExpress() != null) {
				sb.append("Express.Persist_ID=").append(getExpress().toString(true)).append(" ");
			} else {
				sb.append("Express=null ");
			}
			if (getPkg() != null) {
				sb.append("Pkg.Persist_ID=").append(getPkg().toString(true)).append(" ");
			} else {
				sb.append("Pkg=null ");
				sb.append("Status=").append(getStatus()).append(" ");
				sb.append("]");
			}
			return sb.toString();
		}
	}
	
	public static final class STATUS{
		/**
		 * 快递在包裹中
		 */
		public static final int STATUS_ACTIVE = 0;
		/**
		 * 快递不在包裹中
		 */
		public static final int STATUS_OUTOF_PACKAGE = 1;
	}
}
