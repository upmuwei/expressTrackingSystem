package com.expressTracking.entity;


import java.io.Serializable;
import java.util.Date;

/**
 * 包裹路线
 */
public class PackageRoute implements Serializable {

	private static final long serialVersionUID = -120165903842914592L;

	public PackageRoute() {
	}

	private int sn;

	private String packageId;

	private float x;

	private float y;

	private Date tm;
	
	public void setSn(int value) {
		this.sn = value;
	}
	
	public int getSn() {
		return sn;
	}
	
	public int getORMID() {
		return getSn();
	}
	
	public void setX(float value) {
		this.x = value;
	}
	
	public float getX() {
		return x;
	}
	
	public void setY(float value) {
		this.y = value;
	}
	
	public float getY() {
		return y;
	}
	
	public void setTm(Date value) {
		this.tm = value;
	}
	
	public Date getTm() {
		return tm;
	}

	public String getPackageId() {
		return packageId;
	}

	public void setPackageId(String packageId) {
		this.packageId = packageId;
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

			return "PackageRoute[ " + "SN=" +
					getSn() + " " +
					"packageId=" + getPackageId() + " " +
					"X=" + getX() + " " +
					"Y=" + getY() + " " +
					"Tm=" + getTm() + " " +
					"]";
		}
	}
	
}
