package com.my.blog.vo;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.my.blog.enums.ResultCode;

import java.io.Serializable;


public class ResultVo<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    //状态码
    private int code;

    //状态信息
    private String msg;

    //数据

    private T data;

    public ResultVo() {
    }

    //不手动设置状态信息默认成功返回
    public ResultVo(T data) {
        this.code = ResultCode.SUCCESS.getCode();
        this.msg = ResultCode.SUCCESS.getMsg();
        this.data = data;
    }

    public ResultVo(ResultCode resultCode, T data) {
        this.code = resultCode.getCode();
        this.msg = resultCode.getMsg();
        this.data = data;
    }

    //只返回状态码
    public ResultVo(ResultCode resultCode) {
        this.code = resultCode.getCode();
        this.msg = resultCode.getMsg();
        this.data = null;
    }

    public void setData(T data) {
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

   /* public T getData(TypeReference<T> typeReference) {

        ObjectMapper objectMapper = new ObjectMapper();

        return objectMapper.convertValue(data,typeReference);
    }*/

    public T getData() {
        return data;
    }
}
