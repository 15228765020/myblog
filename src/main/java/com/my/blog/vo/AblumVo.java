package com.my.blog.vo;

import com.my.blog.po.Photo;

import java.io.Serializable;
import java.util.List;

public class AblumVo implements Serializable {

    private static final long serialVersionUID = 1L;

    private String cityName;

    private List<Photo> photos;

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public List<Photo> getPhotos() {
        return photos;
    }

    public void setPhotos(List<Photo> photos) {
        this.photos = photos;
    }
}
