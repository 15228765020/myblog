package com.my.blog.service;

import com.my.blog.po.Blog;
import com.my.blog.vo.BlogQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;


public interface BlogService {
    //增加
    Blog addBlog(Blog blog);

    //删除
    void  deleteById(Long id);

    //修改
    Blog updateBlog(Long id ,Blog blog);

    //查一个
    Blog findBlog(Long id);

    Blog findBlogByTitle(String blogTitle);
    Blog getOneConvert(Long id);
    //查所有
    Page<Blog> listBlog(Pageable pageable, BlogQuery blog);

    Page<Blog> listBlog(Pageable pageable);

    Page<Blog> listBlog(String query , Pageable pageable);

    Page<Blog> listBlog(Long tagId,Pageable pageable);

    Map<String,List<Blog>> archiveBlog();

    List<Blog> listRecommendTopBlogs(Integer size);

    Page<Blog> listLatestBlogs(Integer size);

    List<Blog> searchTitle(String title);

    Long countBlog();
}
