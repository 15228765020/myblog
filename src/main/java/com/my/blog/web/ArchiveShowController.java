package com.my.blog.web;

import com.my.blog.po.Blog;
import com.my.blog.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.Map;

@Controller
public class ArchiveShowController {

    @Autowired
    private BlogService blogService;

    @GetMapping("/archives")
    public String archives(Model model){

        Map<String, List<Blog>> map = blogService.archiveBlog();
        //博客的年份对应的博客map集合
        model.addAttribute("map",map);
        //博客总数
        model.addAttribute("count",blogService.countBlog());
        return "archives";
    }
}
