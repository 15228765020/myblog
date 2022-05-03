package com.my.blog.service;

import com.my.blog.NotFoundException;
import com.my.blog.dao.TagRepository;
import com.my.blog.po.Tag;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


@Service
public class TagServiceImpl implements TagService {
    @Autowired
    private TagRepository tagRepository;

    @Transactional
    @Override
    public Tag saveTag(Tag type) {
        return tagRepository.save(type);
    }

    @Transactional
    @Override
    public void deleteTag(Long id) {
        tagRepository.deleteById(id);
    }

    @Transactional
    @Override
    public Tag updateTag(Long id, Tag type) {
        //注意修改的流程！
        Tag type1 = tagRepository.getOne(id);
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
        return tagRepository.save(type1);
        }
    }

    @Override
    public Tag findByName(String name) {
        return tagRepository.findByName(name);
    }

    @Transactional
    @Override
    public Tag getTag(Long id) {
        return tagRepository.getOne(id);
    }
    @Transactional
    @Override
    public Page<Tag> listTag(Pageable pageable) {
        return tagRepository.findAll(pageable);
    }

    @Override
    public List<Tag> listTag() {

        return tagRepository.findAll();
    }

    @Override
    public List<Tag> listTag(String ids) { //1,2,3 多个标签
        return tagRepository.findAllById(convertToList(ids));
    }

    @Override
    public List<Tag> listTopTag(Integer size) {
        Sort sort = Sort.by(Sort.Direction.DESC, "blogs.size");
        Pageable pageable = PageRequest.of(0,size,sort);
        return tagRepository.findTop(pageable);
    }

    private List<Long> convertToList(String ids){
        List<Long> list = new ArrayList<>();
        if (!"".equals(ids)&&ids !=null)
        {
            String[] strings = ids.split(",");
            for (int i = 0; i < strings.length; i++) {
                list.add(Long.parseLong(strings[i]));
            }
        }
        return list;
    }
}
