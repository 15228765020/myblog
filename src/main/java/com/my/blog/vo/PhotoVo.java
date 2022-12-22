package com.my.blog.vo;

import java.io.Serializable;

public class PhotoVo implements Serializable {

    private static final long serialVersionUID = 1L;


    private Integer id;
    //图片base64数组
    private String title;

    private String imgBase64;

    private String cityName;//照片所在城市

    private Integer cityId;//城市id


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getImgBase64() {
        return imgBase64;
    }

    public void setImgBase64(String imgBase64) {
        this.imgBase64 = imgBase64;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public Integer getCityId() {
        return cityId;
    }

    public void setCityId(Integer cityId) {
        this.cityId = cityId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
