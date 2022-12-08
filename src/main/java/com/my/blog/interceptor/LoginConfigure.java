package com.my.blog.interceptor;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class LoginConfigure implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginHandlerInterceptor())
                .addPathPatterns("/admin/**")//拦截
                .excludePathPatterns("/admin/logout")//不拦截

//                .excludePathPatterns("/admin")
                .excludePathPatterns("/admin/login")//不拦截
                .excludePathPatterns("/admin/loginPage")
                .excludePathPatterns("/admin/index.html")
//                .excludePathPatterns("/static/*")

            ;
    }
}
