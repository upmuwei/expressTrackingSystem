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

	private TransPackage pkg;

	private float x;

	private float y;

	private Date tm;
	
	public void setSN(int value) {
		this.sn = value;
	}
	
	public int getSN() {
		return sn;
	}
	
	public int getORMID() {
		return getSN();
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
			sb.append("PackageRoute[ ");
			sb.append("SN=").append(getSN()).append(" ");
			if (getPkg() != null) {
				sb.append("Pkg.Persist_ID=").append(getPkg().toString(true)).append(" ");
			}
			else {
				sb.append("Pkg=null ");
				sb.append("X=").append(getX()).append(" ");
				sb.append("Y=").append(getY()).append(" ");
				sb.append("Tm=").append(getTm()).append(" ");
				sb.append("]");
			}
			return sb.toString();
		}
	}
	
}
