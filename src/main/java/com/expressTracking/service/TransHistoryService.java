package com.expressTracking.service;

import com.expressTracking.entity.TransHistory;

import java.util.List;

public interface TransHistoryService {

    public List<TransHistory> getTransHistory(String expressSheetId);

    public void save(TransHistory transHistory);

}
