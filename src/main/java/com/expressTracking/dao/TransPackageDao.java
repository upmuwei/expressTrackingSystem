package com.expressTracking.dao;

import com.expressTracking.entity.TransPackage;
import org.springframework.stereotype.Repository;

@Repository
public interface TransPackageDao {
    public int update(TransPackage transPackage);

  //  public int delete(int id);

    public void insert(TransPackage transPackage);

    public TransPackage get(String id);

 //   public List<TransPackage> getAll();
}
