package com.expressTracking.entity;


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
	private String id;

	/**
	 * 快件类型
	 */
	private int type;

	/**
	 * 寄件人id
	 */
	private int senderId;

	/**
	 * 收件人id
	 */
	private int receverId;

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
	private float weight;

	/**
	 * 运费
	 */
	private float tranFee;

	/**
	 * 包装费
	 */
	private float packageFee;

	/**
	 * 保险费
	 */
	private float insuFee;

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
	private int status;
	
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
	
	public void setWeight(float value) {
		this.weight = value;
	}
	
	public float getWeight() {
		return weight;
	}
	
	public void setTranFee(float value) {
		this.tranFee = value;
	}
	
	public float getTranFee() {
		return tranFee;
	}
	
	public void setPackageFee(float value) {
		this.packageFee = value;
	}
	
	public float getPackageFee() {
		return packageFee;
	}
	
	public void setInsuFee(float value) {
		this.insuFee = value;
	}
	
	public float getInsuFee() {
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
	
	public void setStatus(int value) {
		this.status = value;
	}
	
	public int getStatus() {
		return status;
	}

	public int getSenderId() {
		return senderId;
	}

	public void setSenderId(int senderId) {
		this.senderId = senderId;
	}

	public int getReceverId() {
		return receverId;
	}

	public void setReceverId(int receverId) {
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
