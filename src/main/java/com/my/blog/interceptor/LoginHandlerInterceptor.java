package com.my.blog.interceptor;

import com.my.blog.po.User;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class LoginHandlerInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        User user = (User)request.getSession().getAttribute("user");
        //查询的session的用户为空，拦截此次请求
        if (user==null)
        {
            //重定向到登录页
            response.sendRedirect("/admin");
            return false;
        }
        return true;
    }
}
