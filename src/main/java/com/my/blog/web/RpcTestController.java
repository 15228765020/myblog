package com.my.blog.web;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RpcTestController {
    @RequestMapping("/rpc")

    private String testRpc(){

        return "Hello World!!";
    }
}
