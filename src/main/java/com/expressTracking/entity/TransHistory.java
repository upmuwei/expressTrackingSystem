package com.expressTracking.entity;


import com.google.gson.annotations.Expose;

import java.io.Serializable;
import java.util.Date;

/**
 *@ClassName: TransHistory
 *@Description: 包裹历史
 *@Author:liwei
 *@Date:2019/5/4 8:49
 *@Version: 1.0
 */
public class TransHistory implements Serializable {


    private static final long serialVersionUID = 6973429316324711538L;

    public TransHistory() {
    }

    public TransHistory(String packageId, Date actTime, int uIdFrom, int uIdTo, Float x, Float y) {
        this.packageId = packageId;
        this.actTime = actTime;
        this.uIdFrom = uIdFrom;
        this.uIdTo = uIdTo;
        this.x = x;
        this.y = y;
    }

    /**
     * 主键，自动生成
     */
    @Expose
    private int sn;
    /**
     * 包裹编号
     */
    @Expose
    private String packageId;

    /**
     * 接收时间
     */
    @Expose
    private Date actTime;

    /**
     * 交付人id
     */
    @Expose
    private int uIdFrom;

    /**
     * 接收人id
     */
    @Expose
    private int uIdTo;

    /**
     * 交付的地址
     */
    @Expose
    private Float x;

    @Expose
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

    public int getuIdFrom() {
        return uIdFrom;
    }

    public void setuIdFrom(int uIdFrom) {
        this.uIdFrom = uIdFrom;
    }

    public int getuIdTo() {
        return uIdTo;
    }

    public void setuIdTo(int uIdTo) {
        this.uIdTo = uIdTo;
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
        } else {
            return "TransHistory[ " +
                    "SN=" + getSn() + " " +
                    "PackageId=" + getPackageId() + " " +
                    "Pkg=null " +
                    "ActTime=" + getActTime() + " " +
                    "UIDFrom=" + getuIdFrom() + " " +
                    "UIDTo=" + getuIdTo() + " " +
                    "X=" + getX() + " " +
                    "Y=" + getY() + " " +
                    "]";
        }
    }

}
