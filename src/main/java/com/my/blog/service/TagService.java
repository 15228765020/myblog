package com.my.blog.service;

import com.my.blog.po.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TagService {
    Tag saveTag(Tag tag);

    void deleteTag(Long id);

    Tag updateTag(Long id, Tag tag);

    Tag findByName(String name);
    //查一个
    Tag getTag(Long id);
    //查所有
    Page<Tag> listTag(Pageable pageable);

    List<Tag> listTag();

    //根据tagIds查标签
    List<Tag> listTag(String ids);

    List<Tag> listTopTag(Integer size);

}
