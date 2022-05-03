package com.my.blog.vo.fileupload;

import com.alibaba.excel.annotation.ExcelProperty;

public class fileVo {
    @ExcelProperty(value = "姓名")
    private String name;
    @ExcelProperty(value = "性别",converter = NumberConverter.class)
    private Integer sex;
    @ExcelProperty(value = "年龄")
    private Integer age;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }
}
