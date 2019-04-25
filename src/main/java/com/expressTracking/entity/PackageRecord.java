package com.expressTracking.entity;

import java.io.Serializable;

/**
 * @author muwei
 * @date 2019/4/25
 */
public class PackageRecord implements Serializable {

    private static final long serialVersionUID = 4564468784876984512L;

    private int sn;

    private String packageId;

    private int uId;

    /**
     * 0 创建
     * 1 打包
     * 2 转运
     * 3 拆包
     */
    private int operation;

    public PackageRecord() {

    }

    public PackageRecord(int sn, String packageId, int uId, int operation) {
        this.sn = sn;
        this.packageId = packageId;
        this.uId = uId;
        this.operation = operation;
    }

    public int getSn() {
        return sn;
    }

    public void setSn(int sn) {
        this.sn = sn;
    }

    public String getPackageId() {
        return packageId;
    }

    public void setPackageId(String packageId) {
        this.packageId = packageId;
    }

    public int getuId() {
        return uId;
    }

    public void setuId(int uId) {
        this.uId = uId;
    }

    public int getOperation() {
        return operation;
    }

    public void setOperation(int operation) {
        this.operation = operation;
    }
}
