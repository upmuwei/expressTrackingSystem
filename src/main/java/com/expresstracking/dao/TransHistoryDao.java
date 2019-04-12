package com.expresstracking.dao;

import com.expresstracking.entity.TransHistory;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransHistoryDao {
  //  public int update(TransHistory transHistory);

   // public int delete(int id);

    public void insert(TransHistory transHistory);



    public TransHistory get(String id);

   // public List<TransHistory> getAll();
}
