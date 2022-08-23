package com.my.blog.enums;

import com.my.blog.interfaces.IResult;

public enum  ResultCode implements IResult {

    SUCCESS(10000,"请求成功")
    ,
    FAILED(-10000,"请求失败")
    ,
    VALIDATE_ERROR(10002,"请求校验失败")
    ;
    private int code;
    private String msg;

    ResultCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }


    @Override
    public int getCode() {
        return code;
    }

    @Override
    public String getMsg() {
        return msg;
    }
}
