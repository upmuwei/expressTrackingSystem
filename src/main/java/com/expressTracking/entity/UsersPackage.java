package com.expressTracking.entity;


import com.google.gson.annotations.Expose;

import java.io.Serializable;

public class UsersPackage implements Serializable {

    private static final long serialVersionUID = -6753829022427770282L;

    public UsersPackage() {
    }

    @Expose
    private int sn;
    @Expose
    private int userUid;
    @Expose
    private String packageId;

    public void setSn(int value) {
        this.sn = value;
    }

    public int getSn() {
        return sn;
    }

    public int getORMID() {
        return getSn();
    }

    public int getUserUid() {
        return userUid;
    }

    public void setUserUid(int userUid) {
        this.userUid = userUid;
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
            return "UsersPackage[ " + "SN=" +
                    getSn() + " " +
                    "userUid=" + getUserUid() +
                    " " + "packageId=" +
                    getPackageId() + " " +
                    "]";
        }
    }

}
