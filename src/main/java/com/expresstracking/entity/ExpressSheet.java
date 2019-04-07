package com.expresstracking.entity;

import java.io.Serializable;
import java.util.Date;


public class ExpressSheet implements Serializable {

	private static final long serialVersionUID = 4941503468986892397L;

	public ExpressSheet() {
	}


	private String id;

	/**
	 * 快件类型
	 */
	private int type;

	/**
	 * 寄件人
	 */
	private CustomerInfo sender;

	/**
	 * 收件人
	 */
	private CustomerInfo recever;

	/**
	 * 重量
	 */
	private Float weight;

	/**
	 * 运费
	 */
	private Float tranFee;

	/**
	 * 包装费
	 */
	private Float packageFee;

	/**
	 * 保险费
	 */
	private Float insuFee;

	/**
	 * 揽收人
	 */
	private String accepter;

	/**
	 * 交付人
	 */
	private String deliver;

	/**
	 * 揽收时间
	 */
	private Date accepteTime;

	/**
	 * 交付时间
	 */
	private Date deliveTime;

	/**
	 * 寄件附件（签名）
	 */
	private String acc1;

	/**
	 * 收件附件（签名）
	 */
	private String acc2;

	/**
	 * 快递状态
	 */
	private Integer status;
	
	public void setId(String value) {
		this.id = value;
	}
	
	public String getId() {
		return id;
	}
	
	public String getORMId() {
		return getId();
	}
	
	public void setType(int value) {
		this.type = value;
	}
	
	public int getType() {
		return type;
	}
	
	public void setWeight(Float value) {
		this.weight = value;
	}
	
	public Float getWeight() {
		return weight;
	}
	
	public void setTranFee(Float value) {
		this.tranFee = value;
	}
	
	public Float getTranFee() {
		return tranFee;
	}
	
	public void setPackageFee(Float value) {
		this.packageFee = value;
	}
	
	public Float getPackageFee() {
		return packageFee;
	}
	
	public void setInsuFee(Float value) {
		this.insuFee = value;
	}
	
	public Float getInsuFee() {
		return insuFee;
	}
	
	public void setAccepter(String value) {
		this.accepter = value;
	}
	
	public String getAccepter() {
		return accepter;
	}
	
	public void setDeliver(String value) {
		this.deliver = value;
	}
	
	public String getDeliver() {
		return deliver;
	}
	
	public void setAccepteTime(Date value) {
		this.accepteTime = value;
	}
	
	public Date getAccepteTime() {
		return accepteTime;
	}
	
	public void setDeliveTime(Date value) {
		this.deliveTime = value;
	}
	
	public Date getDeliveTime() {
		return deliveTime;
	}
	
	public void setAcc1(String value) {
		this.acc1 = value;
	}
	
	public String getAcc1() {
		return acc1;
	}
	
	public void setAcc2(String value) {
		this.acc2 = value;
	}
	
	public String getAcc2() {
		return acc2;
	}
	
	public void setStatus(Integer value) {
		this.status = value;
	}
	
	public Integer getStatus() {
		return status;
	}
	
	public void setSender(CustomerInfo value) {
		this.sender = value;
	}
	
	public CustomerInfo getSender() {
		return sender;
	}
	
	public void setRecever(CustomerInfo value) {
		this.recever = value;
	}
	
	public CustomerInfo getRecever() {
		return recever;
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
			sb.append("ExpressSheet[ ");
			sb.append("ID=").append(getId()).append(" ");
			sb.append("Type=").append(getType()).append(" ");
			if (getSender() != null) {
				sb.append("Sender.Persist_ID=").append(getSender().toString(true)).append(" ");
			}
			else {
				sb.append("Sender=null ");
			}
			if (getRecever() != null) {
				sb.append("Recever.Persist_ID=").append(getRecever().toString(true)).append(" ");
			}
			else {
				sb.append("Recever=null ");
			}
			sb.append("Weight=").append(getWeight()).append(" ");
			sb.append("TranFee=").append(getTranFee()).append(" ");
			sb.append("PackageFee=").append(getPackageFee()).append(" ");
			sb.append("InsuFee=").append(getInsuFee()).append(" ");
			sb.append("Accepter=").append(getAccepter()).append(" ");
			sb.append("Deliver=").append(getDeliver()).append(" ");
			sb.append("AccepterTime=").append(getAccepteTime()).append(" ");
			sb.append("DeliveTime=").append(getDeliveTime()).append(" ");
			sb.append("Acc1=").append(getAcc1()).append(" ");
			sb.append("Acc2=").append(getAcc2()).append(" ");
			sb.append("Status=").append(getStatus()).append(" ");
			sb.append("]");
			return sb.toString();
		}
	}

	public static final class STATUS{
		/**
		 * 新建
		 */
		public static final int STATUS_CREATED = 0;
		/**
		 * 揽收
		 */
		public static final int STATUS_RECEIVED = 1;
		/**
		 * 分拣
		 */
		public static final int STATUS_PARTATION = 2;
		/**
		 * 转运
		 */
		public static final int STATUS_TRANSPORT = 3;
		/**
		 * 派送
		 */
		public static final int STATUS_DISPATCHED = 4;
		/**
		 * 交付
		 */
		public static final int STATUS_DELIVERIED = 5;
	}
}
