package com.my.blog.service;

import com.my.blog.po.VisitorRecords;
import com.my.blog.vo.VisitorRecordsVo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface VisitorRecordsService {

    //根据ip找一行记录
    VisitorRecords findByIp(String ip);

    VisitorRecords save(VisitorRecords visitorRecords);

    Page<VisitorRecordsVo> findByConditon(Pageable pageable, String keyWord);
}
