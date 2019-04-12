package com.expressTracking.dao;

import com.expressTracking.entity.TransHistory;
import org.springframework.stereotype.Repository;

@Repository
public interface TransHistoryDao {
  //  public int update(TransHistory transHistory);

   // public int delete(int id);

    public void insert(TransHistory transHistory);



    public TransHistory get(String id);

   // public List<TransHistory> getAll();
}
