package com.expresstracking.entity;


import java.io.Serializable;
import java.util.Date;


public class TransHistory implements Serializable {

	private static final long serialVersionUID = 6973429316324711538L;

	public TransHistory() {
	}

	/**
	 * 主键，自动生成
	 */
	private int sn;

	private TransPackage pkg;

	/**
	 * 接收时间
	 */
	private Date actTime;

	/**
	 * 被接收人id
	 */
	private int uIdFrom;

	/**
	 * 接收人id
	 */
	private int uIdTo;

	private Float x;

	private Float y;
	
	public void setSn(int value) {
		this.sn = value;
	}
	
	public int getSn() {
		return sn;
	}
	
	public int getORMID() {
		return getSn();
	}
	
	public void setActTime(Date value) {
		this.actTime = value;
	}
	
	public Date getActTime() {
		return actTime;
	}
	
	public void setUIdFrom(int value) {
		this.uIdFrom = value;
	}
	
	public int getUIdFrom() {
		return uIdFrom;
	}
	
	public void setUIdTo(int value) {
		this.uIdTo = value;
	}
	
	public int getuIdTo() {
		return uIdTo;
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
			return String.valueOf(getSn());
		}
		else {
			StringBuilder sb = new StringBuilder();
			sb.append("TransHistory[ ");
			sb.append("SN=").append(getSn()).append(" ");
			if (getPkg() != null) {
				sb.append("Pkg.Persist_ID=").append(getPkg().toString(true)).append(" ");
			}
			else {
				sb.append("Pkg=null ");
				sb.append("ActTime=").append(getActTime()).append(" ");
				sb.append("UIDFrom=").append(getUIdFrom()).append(" ");
				sb.append("UIDTo=").append(getuIdTo()).append(" ");
				sb.append("X=").append(getX()).append(" ");
				sb.append("Y=").append(getY()).append(" ");
				sb.append("]");
			}
			return sb.toString();
		}
	}

}
