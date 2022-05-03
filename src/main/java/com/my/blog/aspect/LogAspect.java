package com.my.blog.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@Aspect
@Component
public class LogAspect {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    // 匹配此包下以及其参数的；确定位置
    @Pointcut("execution(* com.my.blog.web.*.*(..))")
    public void  log(){

    }

    //通知前
    @Before("log()")
    public void doBefore(JoinPoint joinPoint){
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();


        String url = String.valueOf(request.getRequestURL());
        String ip  = request.getRemoteAddr();

        String classMethod = joinPoint.getSignature().getDeclaringTypeName()+"."+joinPoint.getSignature().getName();

        Object[] args = joinPoint.getArgs();

        //给请求的参数做一个日志
        RequestLog requestLog = new RequestLog(url, ip, classMethod, args);
        logger.info("-----Request:{}-----",requestLog);
    }
    //
    @After("log()")
    public void doAfter(){
        logger.info("------doBefore-----");
    }
    //通知后返回值
    @AfterReturning(returning = "result",pointcut = "log()")
    public void doAfterReturn(Object result){
        logger.info("-------Request:{}-------",result);
    }

    private  class RequestLog{
       private String url;
       private String ip;
       private String classMethod;
       private Object[] args;

        public RequestLog(String url, String ip, String classMethod, Object[] args) {
            this.url = url;
            this.ip = ip;
            this.classMethod = classMethod;
            this.args = args;
        }

        @Override
        public String toString() {
            return "RequestLog{" +
                    "url='" + url + '\'' +
                    ", ip='" + ip + '\'' +
                    ", classMethod='" + classMethod + '\'' +
                    ", args=" + Arrays.toString(args) +
                    '}';
        }
    }
}
