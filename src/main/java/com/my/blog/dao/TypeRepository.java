package com.my.blog.dao;

import com.my.blog.po.Type;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TypeRepository extends JpaRepository<Type,Long> {
    Type findByName(String name);
    //按照指定规则查
    @Query(("select t from Type  t"))
    List<Type> findTop(Pageable pageable);
}
