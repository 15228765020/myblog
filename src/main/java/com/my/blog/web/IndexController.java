package com.my.blog.web;

import com.alibaba.fastjson.JSON;
import com.my.blog.po.Address;
import com.my.blog.po.Blog;
import com.my.blog.po.VisitorRecords;
import com.my.blog.service.BlogService;
import com.my.blog.service.TagService;
import com.my.blog.service.TypeService;

import com.my.blog.service.VisitorRecordsService;
import com.my.blog.util.HttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@Controller

public class IndexController {
    @Autowired
    private BlogService blogService;
    @Autowired
    private TypeService typeService;
    @Autowired
    private TagService tagService;

    @Autowired
    private VisitorRecordsService visitorRecordsService;

    private Logger logger = LoggerFactory.getLogger(IndexController.class);


    @Value("${baiduApi.accessKey}")
    private String ak;

    private String ip;



    @RequestMapping("/")
    public String index(@PageableDefault(sort ={"updateTime"},size = 8,direction = Sort.Direction.DESC) Pageable pageable, Model model){
    //typeService.listType()

        Page<Blog> page = blogService.listBlog(pageable);
        //管理请求的url 管理请求人的url
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();

        HttpServletRequest request = null;
        try {
                           request = requestAttributes.getRequest();
        }catch (Exception e)
        {
            logger.error(e.getMessage());
        }
        //获取当前请求的ip 175.153.169.65
        String vistorIp = request.getRemoteAddr();
        // String vistorIp = "175.153.169.65";
        ip = vistorIp;

        //先查数据库是否已经存在当前的ip
        VisitorRecords visitorRecords =null;
        VisitorRecords save1Res =null;
        VisitorRecords visitorRecords1 =null;
        try {
                visitorRecords = visitorRecordsService.findByIp(vistorIp);

                //更新访问时间
                visitorRecords.setLastVisitTime(new Date());

                Integer totalVisitNumber = visitorRecords.getTotalVisitNumber();
                totalVisitNumber++;
                visitorRecords.setTotalVisitNumber(totalVisitNumber);
                save1Res = visitorRecordsService.save(visitorRecords);

            }
            catch (Exception e)
            {
                //第一次访问添加数据
                visitorRecords = new VisitorRecords();
                //添加访问次数
                visitorRecords.setTotalVisitNumber(1);
                //通过请求者ip获得它的地址
                Address address = getAddress();
                //访问时间
                visitorRecords.setLastVisitTime(new Date());
                //访问ip
                visitorRecords.setVisitorIp(vistorIp);
                //访问者地址
                visitorRecords.setVisitorAddress(address.getAddress());

                visitorRecords1 =  visitorRecordsService.save(visitorRecords);
                logger.error("这个ip第一次访问"+e.getMessage());
            }

                //非第一次访问保存

            if (save1Res!=null)
            {
                logger.info("非第一次访问保存成功");
            }


            if (visitorRecords1!=null)
            {
                logger.info("添加访问记录成功~~");
            }

        model.addAttribute("page",page);
        model.addAttribute("types",typeService.listTopType(6));
        model.addAttribute("tags",tagService.listTopTag(6));
        model.addAttribute("recommenedBlogs",blogService.listRecommendTopBlogs(8));
        return "index";
    }

    @PostMapping("/search")
    public String search(@PageableDefault(sort ={"updateTime"},size = 5,direction = Sort.Direction.DESC)Pageable pageable, Model model,@RequestParam("query") String query){

        System.out.println("query:"+query);
        model.addAttribute("page",blogService.listBlog("%"+query+"%",pageable));
        model.addAttribute("query",query);
        return "search";
    }

    //首页到博客详情页

    @GetMapping("/blog/{id}")
    public String toDetails(@PathVariable Long id,Model model){
        model.addAttribute("blog",blogService.getOneConvert(id));
        return "blog";
    }
    //去分类页
    @GetMapping("/toType")
    public String toType(){
        return "types";
    }
    //去标签页
    @GetMapping("/toTag")
    public String toTag(){
        return "tags";
    }

    //最新博客展示
    @GetMapping("/footer/newBlog")
    public String newBlogs(Model model){
        //最新博客取前三条
        Page<Blog> blogs = blogService.listLatestBlogs(3);
        model.addAttribute("newBlogs",blogs);
        return "_fragment :: newBlogList";
    }


    private Address getAddress(){

        final String requestPath = "http://api.map.baidu.com/location/ip?ak="+ak+"&ip="+ip+"&coor=bd09ll";
        //获取百度通过ip获取地址的接口返回数据
        String result = HttpClient.doGet(requestPath);

        Address address = JSON.parseObject(result, Address.class);

        return address;
    }





}
