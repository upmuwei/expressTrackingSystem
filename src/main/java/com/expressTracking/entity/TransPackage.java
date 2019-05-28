package com.expressTracking.entity;

import com.google.gson.annotations.Expose;

import java.io.Serializable;
import java.util.*;


public class TransPackage implements Serializable {

    private static final long serialVersionUID = 3050390478904210174L;

    public TransPackage() {
    }

    public TransPackage(String id, String sourceNode, String targetNode, Date createTime, Integer status) {
        this.id = id;
        this.sourceNode = sourceNode;
        this.targetNode = targetNode;
        this.createTime = createTime;
        this.status = status;
    }

    /**
     * 包裹开头字符必须为1
     */
    @Expose
    private String id;

    /**
     * 原节点
     */
    @Expose
    private String sourceNode;

    /**
     * 目的节点
     */
    @Expose
    private String targetNode;

    /**
     * 创建时间
     */
    @Expose
    private Date createTime;

    /**
     * 0,新建
     * 1,打包
     * 2,转运
     * 3,分拣
     * 4,揽收货篮
     * 5,派送货篮
     * 6,已拆包
     */
    @Expose
    private Integer status;


    /**
     * 包裹的位置信息
     */
//    @Expose
//    private List<PackageRoute> route = new ArrayList<>();
    /**
     * 包裹的操作者
     */
    @Expose
    private UsersPackage user;

    /**
     * 包裹中的快件内容
     */
    @Expose
    private List<TransPackageContent> content = new ArrayList<>();

    /**
     * 包裹的历史记录
     */
    @Expose
    private List<TransHistory> history = new ArrayList<>();

    //新建
    public static final int PACKAGE_NEW = 0;
    //打包
    public static final int PACKAGE_PACK = 1;
    //转运
    public static final int PACKAGE_TRANS = 2;
    //分拣
    public static final int PACKAGE_SORTING = 3;
    //揽收货篮
    public static final int PACKAGE_COLLECT = 4;
    //派送货篮
    public static final int PACKAGE_DELIVER = 5;
    //包裹已拆包，被废弃的状态
    public static final int PACKAGE_COMPLETE = 6;


    public void setId(String value) {
        this.id = value;
    }

    public String getId() {
        return id;
    }

    public String getORMId() {
        return getId();
    }

    public void setSourceNode(String value) {
        this.sourceNode = value;
    }

    public String getSourceNode() {
        return sourceNode;
    }

    public void setTargetNode(String value) {
        this.targetNode = value;
    }

    public String getTargetNode() {
        return targetNode;
    }

    public void setCreateTime(Date value) {
        this.createTime = value;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setStatus(Integer value) {
        this.status = value;
    }

    public Integer getStatus() {
        return status;
    }

//    public void setRoute(List<PackageRoute> value) {
//        this.route = value;
//    }
//
//    public List<PackageRoute> getRoute() {
//        return route;
//    }

    public void setUser(UsersPackage value) {
        this.user = value;
    }

    public UsersPackage getUser() {
        return user;
    }

    public void setContent(List<TransPackageContent> value) {
        this.content = value;
    }

    public List<TransPackageContent> getContent() {
        return content;
    }


    public void setHistory(List<TransHistory> value) {
        this.history = value;
    }

    public List<TransHistory> getHistory() {
        return history;
    }


    @Override
    public String toString() {
        return toString(false);
    }

    public String toString(boolean idOnly) {
        if (idOnly) {
            return String.valueOf(getId());
        } else {
            StringBuilder sb = new StringBuilder();
            sb.append("TransPackageService[ ");
            sb.append("ID=").append(getId()).append(" ");
            sb.append("SourceNode=").append(getSourceNode()).append(" ");
            sb.append("TargetNode=").append(getTargetNode()).append(" ");
            sb.append("CreateTime=").append(getCreateTime()).append(" ");
            sb.append("Status=").append(getStatus()).append(" ");
            if (getUser() != null) {
                sb.append("User.Persist_ID=").append(getUser().toString(true)).append(" ");
            } else {
                sb.append("User=null ");
                sb.append("Content.size=").append(getContent().size()).append(" ");
                sb.append("History.size=").append(getHistory().size()).append(" ");
                sb.append("]");
            }
            return sb.toString();
        }
    }
}
