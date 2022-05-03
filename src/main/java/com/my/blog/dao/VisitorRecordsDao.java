package com.my.blog.dao;

import com.my.blog.po.VisitorRecords;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface VisitorRecordsDao  extends JpaRepository<VisitorRecords,Integer>, JpaSpecificationExecutor<VisitorRecords> {
    VisitorRecords findByVisitorIp(String ip);
}
