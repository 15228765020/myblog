package com.my.blog.service;

import com.my.blog.po.Type;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TypeService {
    Type saveType(Type type);

    void deleteType(Long id);

    Type updateType(Long id,Type type);

    Type findByName(String name);
    //查一个
    Type getType(Long id);
    //查所有
    Page<Type> listType(Pageable pageable);

    List<Type> listType();
    List<Type> listTopType(Integer size);

}
