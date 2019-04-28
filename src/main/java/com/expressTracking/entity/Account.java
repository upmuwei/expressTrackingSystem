package com.expressTracking.entity;

import java.io.Serializable;

/**
 * @author muwei
 */
public class Account implements Serializable {

    private static final long serialVersionUID = -5442000268601430622L;

    private String id;

    private String password;

    public Account() {

    }

    public Account(String id, String password) {
        this.id = id;
        this.password = password;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
