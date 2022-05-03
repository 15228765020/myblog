package com.my.blog.service;

import com.my.blog.po.User;

public interface UserService {
    User checkUser(String username,String password);
}
