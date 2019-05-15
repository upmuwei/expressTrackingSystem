package com.expressTracking.entity;


import com.google.gson.annotations.Expose;

import java.io.Serializable;
import java.util.Date;

/**
 * @author muwei
 * @date 2019/4/5
 * 包裹路线
 */
public class PackageRoute implements Serializable {

    private static final long serialVersionUID = -120165903842914592L;

    public PackageRoute() {
    }

    @Expose
    private int sn;

    private String packageId;
    @Expose
    private double x;
    @Expose
    private double y;
    @Expose
    private Date tm;

    public void setSn(int value) {
        this.sn = value;
    }

    public int getSn() {
        return sn;
    }

    public void setX(double value) {
        this.x = value;
    }

    public double getX() {
        return x;
    }

    public void setY(double value) {
        this.y = value;
    }

    public double getY() {
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
        } else {

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
