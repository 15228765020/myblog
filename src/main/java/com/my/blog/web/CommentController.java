package com.my.blog.web;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
import com.my.blog.po.Comment;
import com.my.blog.po.User;
import com.my.blog.service.BlogService;
import com.my.blog.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.*;

@Controller
public class CommentController {

    @Autowired
    private CommentService commentService;
    @Autowired
    private BlogService blogService;

    // 暂时使用定义在yml的固定图片
    @Value("${comment.avatar}")
    private String avatar;

    //WxPusher UID
    @Value("${wxpusher.uid}")
    private String uid;
    //APP_TOKEN
    @Value("${wxpusher.app_token}")
    private String appToken;

    @GetMapping("/comments/{blogId}")
    public String comments(@PathVariable Long blogId, Model model){
        System.out.println("博客ID:"+blogId);
        List<Comment> comments = commentService.listCommentByBlogId(blogId);
        model.addAttribute("comments",comments);
//        System.out.println(comments);
//        System.out.println("comments的值"+comments);
//        System.out.println("传输commentList数据成功!!");
        return "blog::commentList";
    }
/*    @GetMapping("/comments/{blogId}")
    @ResponseBody
    public List<Comment> comments(@PathVariable Long blogId, Model model){
        System.out.println("博客ID:"+blogId);
        List<Comment> comments = commentService.listCommentByBlogId(blogId);
//        model.addAttribute("comments",comments);
//        System.out.println(comments);
//        System.out.println("comments的值"+comments);
//        System.out.println("传输commentList数据成功!!");
        return comments;*/
 //    }
    @PostMapping("/comments")
    public  String post( @Valid Comment comment, BindingResult bindingResult, ModelMap map, HttpSession session, HttpServletRequest request){



        //首先校验传过来的评论相关的信息
        if (bindingResult.hasErrors()){

            List<FieldError> fieldErrors = bindingResult.getFieldErrors();

            for (FieldError fieldError : fieldErrors) {

                //昵称
                if ("nickname".equals(fieldError.getField())){
                    map.put("nicknameError",fieldError.getDefaultMessage());
                }else if ("email".equals(fieldError.getField())){
                    //邮箱
                    map.put("emailError",fieldError.getDefaultMessage());

                }else if ("content".equals(fieldError.getField())){
                    //内容
                    map.put("contentError",fieldError.getDefaultMessage());

                }

            }

            //返回博客详情页
            return "blog::replyArea";

        }


        Long blogId = comment.getBlog().getId();
        comment.setBlog(blogService.findBlog(blogId));

        User user = (User) session.getAttribute("user");
        //如果是博主留言
        if (comment.isAdminComment()){
            comment.setAvatar(user.getAvatar());
            comment.setAdminComment(true);
           // comment.setNickname();
        }else{
            comment.setAvatar(avatar);
        }
        //根据设定保存评论
        Comment comment1 = commentService.saveComment(comment);
        if (ObjectUtil.isNotNull(comment1))//评论成功 推送消息给微信
        {
            String reqUrl="http://wxpusher.zjiecode.com/api/send/message";//推送api
            String detailsUrl="http://www.blog4cl.top/blog/"+blogId;//相关博客评论页面url
            //请求的参数
            Map<String, Object> params = new HashMap<>();
            params.put("appToken",appToken);
            params.put("content",comment1.getContent());

            params.put("summary","手心日记有人留言啦!!");
            params.put("contentType",1);

            List<String> list = new ArrayList<>();
            list.add(uid);
            String[] uids = ArrayUtil.toArray(list, String.class);
            /*String[] uids = new String[]{};
            uids[0] = uid;*/
            //发送目标的UID，是一个数组。注意uids和topicIds可以同时填写，也可以只填写一个。
            params.put("uids",uids);
            params.put("url",detailsUrl);

            String jsonParms =  JSONUtil.toJsonStr(params);

            String post =  HttpRequest.post(reqUrl).header("Content-Type","application/json").body(jsonParms).execute().body();

            if (ObjectUtil.isNotNull(post))
            {
                System.out.println("推送消息成功!!~~");
            }
        }else {
            System.err.println("评论失败!!");
        }

        return "redirect:/comments/"+blogId;
    }

}
