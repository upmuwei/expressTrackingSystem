package com.expressTracking.entity;


import com.google.gson.annotations.Expose;

import java.io.Serializable;
import java.util.Date;

/**
 * 快件实体
 * @author muwei
 * @date
 */
public class ExpressSheet implements Serializable {

	private static final long serialVersionUID = 4941503468986892397L;

	public ExpressSheet() {
	}

	/**
	 * 快件开头字符不能为1
	 */
	@Expose
	private String id;

	/**
	 * 快件类型
	 */
	@Expose
	private Integer type;

	/**
	 * 寄件人id
	 */
	@Expose
	private Integer senderId;

	/**
	 * 收件人id
	 */
	@Expose
	private Integer receverId;

	/**
	 * 寄件人
	 */
	@Expose
	private CustomerInfo sender;

	/**
	 * 收件人
	 */
	@Expose
	private CustomerInfo recever;

	/**
	 * 重量
	 */
	@Expose
	private Float weight;

	/**
	 * 运费
	 */
	@Expose
	private Float tranFee;

	/**
	 * 包装费
	 */
	@Expose
	private Float packageFee;

	/**
	 * 保险费
	 */
	@Expose
	private Float insuFee;

	/**
	 * 揽收人
	 */
	@Expose
	private String accepter;

	/**
	 * 交付人
	 */
	@Expose
	private String deliver;

	/**
	 * 揽收时间
	 */
	@Expose
	private Date accepteTime;

	/**
	 * 交付时间
	 */
	@Expose
	private Date deliveTime;

	/**
	 * 寄件附件（签名）
	 */
	@Expose
	private String acc1;

	/**
	 * 收件附件（签名）
	 */
	@Expose
	private String acc2;

	/**
	 * 快递状态
	 */
	@Expose
	private Integer status;

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Integer getSenderId() {
		return senderId;
	}

	public void setSenderId(Integer senderId) {
		this.senderId = senderId;
	}

	public Integer getReceverId() {
		return receverId;
	}

	public void setReceverId(Integer receverId) {
		this.receverId = receverId;
	}

	public CustomerInfo getSender() {
		return sender;
	}

	public void setSender(CustomerInfo sender) {
		this.sender = sender;
	}

	public CustomerInfo getRecever() {
		return recever;
	}

	public void setRecever(CustomerInfo recever) {
		this.recever = recever;
	}

	public Float getWeight() {
		return weight;
	}

	public void setWeight(Float weight) {
		this.weight = weight;
	}

	public Float getTranFee() {
		return tranFee;
	}

	public void setTranFee(Float tranFee) {
		this.tranFee = tranFee;
	}

	public Float getPackageFee() {
		return packageFee;
	}

	public void setPackageFee(Float packageFee) {
		this.packageFee = packageFee;
	}

	public Float getInsuFee() {
		return insuFee;
	}

	public void setInsuFee(Float insuFee) {
		this.insuFee = insuFee;
	}

	public String getAccepter() {
		return accepter;
	}

	public void setAccepter(String accepter) {
		this.accepter = accepter;
	}

	public String getDeliver() {
		return deliver;
	}

	public void setDeliver(String deliver) {
		this.deliver = deliver;
	}

	public Date getAccepteTime() {
		return accepteTime;
	}

	public void setAccepteTime(Date accepteTime) {
		this.accepteTime = accepteTime;
	}

	public Date getDeliveTime() {
		return deliveTime;
	}

	public void setDeliveTime(Date deliveTime) {
		this.deliveTime = deliveTime;
	}

	public String getAcc1() {
		return acc1;
	}

	public void setAcc1(String acc1) {
		this.acc1 = acc1;
	}

	public String getAcc2() {
		return acc2;
	}

	public void setAcc2(String acc2) {
		this.acc2 = acc2;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
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
			return "ExpressSheet[ " +
					"ID=" + getId() + " " +
					"Type=" + getType() + " " +
					"Sender=" + getSender() + " " +
					"Recever=" + getRecever() + " " +
					"Weight=" + getWeight() + " " +
					"TranFee=" + getTranFee() + " " +
					"PackageFee=" + getPackageFee() + " " +
					"InsuFee=" + getInsuFee() + " " +
					"Accepter=" + getAccepter() + " " +
					"Deliver=" + getDeliver() + " " +
					"AccepterTime=" + getAccepteTime() + " " +
					"DeliveTime=" + getDeliveTime() + " " +
					"Acc1=" + getAcc1() + " " +
					"Acc2=" + getAcc2() + " " +
					"Status=" + getStatus() + " " +
					"]";
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
