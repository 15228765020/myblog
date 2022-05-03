package com.my.blog.web;

import com.my.blog.po.Blog;
import com.my.blog.po.Tag;
import com.my.blog.service.BlogService;
import com.my.blog.service.TagService;
import com.my.blog.vo.BlogQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import java.util.List;

@Controller
public class TagShowController {

    @Autowired
    private TagService tagService;
    @Autowired
    private BlogService blogService;

    @GetMapping("/toTag/{id}")
    public String tagShow(@PathVariable Long id,
                           @PageableDefault(sort = {"updateTime"},
                                   direction = Sort.Direction.DESC) Pageable pageable, Model model){
        List<Tag> tagList = tagService.listTopTag(8);
        //第一次进来我们id用的固定参数-1
        if (id==-1){
            id = tagList.get(0).getId();
        }
        //通过标签id查找博客

        Page<Blog> pages = blogService.listBlog(id,pageable);

        model.addAttribute("tags",tagList);
        model.addAttribute("pages",pages);
        //将当前id设置为选中的id 传给前端，前端就有
        model.addAttribute("activeId",id);
        return "tags";
    }


}
