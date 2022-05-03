package com.my.blog.web.admin;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.my.blog.po.Blog;
import com.my.blog.po.Tag;
import com.my.blog.po.Type;
import com.my.blog.po.User;
import com.my.blog.service.BlogService;
import com.my.blog.service.TagService;
import com.my.blog.service.TypeService;
import com.my.blog.vo.BlogQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class BlogController {

    private static final String INPUT="admin/blogs-input";
    private static final String LIST="admin/blogs";
    private static final String REDIRECT_LIST="redirect:/admin/blogs";


    @Autowired
    private BlogService blogService;
    @Autowired
    private TypeService typeService;
    @Autowired
    private TagService tagService;
    @GetMapping("blogs")
    public String blogs(@PageableDefault(sort ={"updateTime"},size = 8,direction = Sort.Direction.DESC) Pageable pageable, Model model, BlogQuery blog){

        Page<Blog> blogs = blogService.listBlog(pageable, blog);
        model.addAttribute("types",typeService.listType());
        model.addAttribute("blogs",blogs);
        return LIST;
    }
    //标题栏搜索方法
    @PostMapping("blogs/search")
    public String search(@PageableDefault(sort ={"updateTime"},size = 8,direction = Sort.Direction.DESC) Pageable pageable, Model model, BlogQuery blog){

        System.out.println(blog);

        Page<Blog> blogs = blogService.listBlog(pageable, blog);
        model.addAttribute("blogs",blogs);
        return "admin/blogs :: blogList";//只刷新表格内容

    }
    //我自己的ajax的控制方法 未完
    //admin/blogs/titleSearch
    @PostMapping("blogs/titleSearch")
    @ResponseBody
    public String titleSearch(@RequestParam String title){
        System.out.println("ajax请求中~~~");
        //查询操作
        List<Blog> list = blogService.searchTitle(title);
        ObjectMapper objectMapper = new ObjectMapper();
        String json =null;
        try {
             json = objectMapper.writeValueAsString(list);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return json;
    }
    //下面的input和eidtInput的公共部分
    private void setTypeAndTag(Model model){
        List<Type> types = typeService.listType();
        List<Tag> tags = tagService.listTag();
        System.out.println("tags:"+tags);
        model.addAttribute("types",types);
        model.addAttribute("tags",tags);
    }

    //blog-input
    @GetMapping("/blogs/input")
    public  String input(Model model){
        //挂上框内需要的值
        setTypeAndTag(model);
        model.addAttribute("blog",new Blog());
        return INPUT;
    }
    //编辑
    @GetMapping("/blogs/{id}/input")
    public  String editInput(@PathVariable Long id, Model model){
        //挂上框内需要的值
        setTypeAndTag(model);
        //
        model.addAttribute("blog",blogService.findBlog(id));
        return INPUT;
    }


    //新增和修改数据的复用逻辑
    @PostMapping("/blogs")
    public String post( Blog blog, RedirectAttributes attributes, HttpSession session){
        blog.setUser((User) session.getAttribute("user"));
        System.out.println(blog);
        //类型
        System.out.println("blog的ID:"+blog.getType().getId());
        blog.setType(typeService.getType(blog.getType().getId()));
        blog.setTags(tagService.listTag(blog.getTagIds()));


        //之前DB有此Blog就执行修改，否则增加
        Blog blogByName = this.blogService.findBlogByTitle(blog.getTitle());
        if (blogByName == null)
        {
            //响应数据1
            try {
                blogService.addBlog(blog);
                attributes.addFlashAttribute("message","新增成功!!");
            } catch (Exception e) {
                attributes.addFlashAttribute("message","新增失败!!");
            }
        }
        else
        {
            try {
                blogService.updateBlog(blog.getId(), blog);
                attributes.addFlashAttribute("message","修改成功!!");
            }   catch (Exception e) {
                attributes.addFlashAttribute("message","操作失败!!请联系开发人员!!");
            }
            //响应数据2
        }
        return REDIRECT_LIST;
    }

    //删除

    @GetMapping("/blogs/{id}/delete")
    public String delete(@PathVariable Long id,RedirectAttributes attributes){//这里用Model报错,（原因这里新的Model域指针指向之前的Model，这里过去后之前的在html页面就取值为空,产生冲突）
        blogService.deleteById(id);
        //查一下
        Blog blog = blogService.findBlog(id);
        if (blog!=null)
        {
            attributes.addFlashAttribute("message","操作成功!!");
        }
        else
        {
            attributes.addFlashAttribute("message","操作失败!!请联系开发人员!!");
        }
        return REDIRECT_LIST;
    }

}
