package com.my.blog.service;

import com.my.blog.po.Photo;
import com.my.blog.vo.AblumVo;
import com.my.blog.vo.PageVo;
import com.my.blog.vo.PhotoVo;
import com.my.blog.vo.ResultVo;
import org.springframework.data.domain.Pageable;

import java.util.List;


public interface PhotoService {
    //查
    PageVo getPage(Pageable pageable);
    //add

    void addPhoto(String[] imgs);


    void delPhotoById(Integer photoId);

    Photo findOne(Integer id);

    void addPhoto(PhotoVo[] photoVos);

    ResultVo<List<AblumVo>> findAblums();

}
