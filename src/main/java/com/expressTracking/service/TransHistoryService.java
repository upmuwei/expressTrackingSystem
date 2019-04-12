package com.expressTracking.service;

import com.expressTracking.entity.TransHistory;

import java.util.List;

public interface TransHistoryService {
//    public String appointTransPorter(String packageId,int nodeUId,int userId);
//
//    public TransHistory appointDeliver(String transPackageId, String expressSheetId);

    public List<TransHistory> getTransHistory(String expressSheetId);

    public void save(TransHistory transHistory);

}
