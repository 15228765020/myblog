package com.my.blog.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
/*
* 跳转到自定义的错误页面
* */
@ControllerAdvice
public class ControllerException {

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @ExceptionHandler(Exception.class)
    public ModelAndView exceptionHandler(HttpServletRequest httpServletRequest,Exception e) throws Exception {
        logger.error("Request URL:{},Exception:{}",httpServletRequest.getRequestURL(),e);
        //如过有ResponseStatus此注解
        if (AnnotationUtils.findAnnotation(e.getClass(), ResponseStatus.class)!=null)
        {
            throw e;

        }

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("url",httpServletRequest.getRequestURL());
        //获取异常信息
        modelAndView.addObject("exception",e);
        modelAndView.setViewName("error/error");
        return modelAndView;
    }
}
