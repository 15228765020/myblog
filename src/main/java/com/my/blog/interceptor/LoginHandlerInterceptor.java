package com.my.blog.interceptor;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.my.blog.po.User;
import com.my.blog.web.IndexController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class LoginHandlerInterceptor implements HandlerInterceptor {

    private Logger logger = LoggerFactory.getLogger(LoginHandlerInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        User user = (User)request.getSession().getAttribute("user");

        //请求的uri
        String requestURI = request.getRequestURI();


        logger.info("requestURI->{}",requestURI);

        //查询的session的用户为空，拦截此次请求
        if (user==null)
        {
            if (StrUtil.isNotEmpty(requestURI)&& requestURI.contains("/admin")){
                //重定向到登录页
                response.sendRedirect("/admin/loginPage");
//            request.getRequestDispatcher("/admin/loginPage").forward(request,response);
//            return true;

            }

        }else if (ObjectUtil.isNotNull(user)&&"/admin".equals(requestURI)){
            //直接到首页
            response.sendRedirect("/admin/index.html");
//            request.getRequestDispatcher("/admin/index.html").forward(request,response);
//            return true;
        }else {
            //其他情况放行
            return true;
        }
        return false;
    }
}
