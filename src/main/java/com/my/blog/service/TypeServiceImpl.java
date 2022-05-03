package com.my.blog.service;

import com.my.blog.NotFoundException;
import com.my.blog.dao.TypeRepository;
import com.my.blog.po.Type;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
public class TypeServiceImpl implements TypeService {
    @Autowired
    private TypeRepository typeRepository;

    @Transactional
    @Override
    public Type saveType(Type type) {
        return typeRepository.save(type);
    }

    @Transactional
    @Override
    public void deleteType(Long id) {
        typeRepository.deleteById(id);
    }

    @Transactional
    @Override
    public Type updateType(Long id, Type type) {
        Type type1 = typeRepository.getOne(id);
        //先根据id查询有没有此id的用户，没有直接抛出异常
        if (type1==null)
        {
            throw new NotFoundException("没有此type存在");
        }
        else
        {
        //将用户修改后的type 再保存type1
        BeanUtils
                .copyProperties
                       (type,type1);
        return typeRepository.save(type1);
        }
    }

    @Override
    public Type findByName(String name) {
        return typeRepository.findByName(name);
    }

    @Transactional
    @Override
    public Type getType(Long id) {
        return typeRepository.getOne(id);
    }
    @Transactional
    @Override
    public Page<Type> listType(Pageable pageable) {
        return typeRepository.findAll(pageable);
    }

    @Override
    public List<Type> listType() {
        return typeRepository.findAll();
    }

    @Override
    public List<Type> listTopType(Integer size) {
        Sort sort = Sort.by(Sort.Direction.DESC, "blogs.size");
        Pageable pageable = PageRequest.of(0, size,sort);

        return typeRepository.findTop(pageable);
    }


}
