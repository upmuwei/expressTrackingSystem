package com.expressTracking.entity;

import java.io.Serializable;

/**
 * @author muwei
 */
public class Account implements Serializable {

    private static final long serialVersionUID = -5442000268601430622L;

    private String phone;

    private String password;

    public Account() {

    }

    public Account(String phone, String password) {
        this.phone = phone;
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
