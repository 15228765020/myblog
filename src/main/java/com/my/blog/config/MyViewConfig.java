package com.my.blog.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MyViewConfig implements WebMvcConfigurer {

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        //我的相册
        registry.addViewController("/album").setViewName("photo_album.html");

        //
        registry.addViewController("/admin/album/add.html").setViewName("admin/photo_album_add.html");
    }
}
