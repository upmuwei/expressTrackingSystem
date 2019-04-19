package com.expressTracking.entity;


import java.io.Serializable;
import java.util.Objects;

/**
 * 包裹内容实体类
 * @author muwei
 * @date 2019/4/5
 */
public class TransPackageContent implements Serializable {

	private static final long serialVersionUID = -2696910600791838998L;

	public TransPackageContent() {
	}

	private int sn;

	private String expressId;

	private String packageId;

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

	public String getExpressId() {
		return expressId;
	}

	public void setExpressId(String expressId) {
		this.expressId = expressId;
	}

	public String getPackageId() {
		return packageId;
	}

	public void setPackageId(String packageId) {
		this.packageId = packageId;
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
			if (getExpressId() != null) {
				sb.append("Express.Persist_ID=").append(getExpressId()).append(" ");
			} else {
				sb.append("Express=null ");
			}
			if (getPackageId() != null) {
				sb.append("Pkg.Persist_ID=").append(getPackageId()).append(" ");
			} else {
				sb.append("Pkg=null ");
				sb.append("Status=").append(getStatus()).append(" ");
				sb.append("]");
			}
			return sb.toString();
		}
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (!(o instanceof TransPackageContent)) {
			return false;
		}
		TransPackageContent that = (TransPackageContent) o;
		return getSn() == that.getSn() &&
				getStatus() == that.getStatus() &&
				Objects.equals(getExpressId(), that.getExpressId()) &&
				Objects.equals(getPackageId(), that.getPackageId());
	}

	@Override
	public int hashCode() {
		return Objects.hash(getSn(), getExpressId(), getPackageId(), getStatus());
	}

	/**
	 * 包裹内容状态
	 */
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
