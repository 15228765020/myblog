package com.my.blog.web.admin;

import com.my.blog.po.User;
import com.my.blog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/admin")//全局访问链接
public class LoginController {

    @Autowired
    private UserService userService;

//    拦截器方法
    @GetMapping
    public String loginPage(){
        return "admin/login";
    }
//RedirectAttributes 重定向的属性变量不被刷掉
    //后台登录
    @PostMapping("/login")
    public String loginCheck(@RequestParam String username, @RequestParam String password,
                             HttpSession session, RedirectAttributes attributes){
        User user = userService.checkUser(username, password);
        if (user!=null)
        {
            System.out.println("查出用户"+user);
            //保证安全，不返回密码到页面
            user.setPassword(null);
            // session.setAttribute("username",username);
            //上面不是存username有user判断，直接返回user
            session.setAttribute("user",user);
            return "admin/index";
        }else
        {
            attributes.addFlashAttribute("message","用户名或密码错误");
            return "redirect:/admin";
        }
    }
    //后台注销
    @GetMapping("/logout")
    public String logout(HttpSession session){
        session.removeAttribute("user");
        return "redirect:/admin";
    }
}
