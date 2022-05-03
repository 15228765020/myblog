package com.my.blog.vo;

public class BlogQuery {
    private String title;
    private Long typeId;
    private Boolean recommend;


    public BlogQuery() {
    }

    public BlogQuery(String title, Long typeId, Boolean recommend) {
        this.title = title;
        this.typeId = typeId;
        this.recommend = recommend;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getTypeId() {
        return typeId;
    }

    public void setTypeId(Long typeId) {
        this.typeId = typeId;
    }

    public Boolean getRecommend() {
        return recommend;
    }

    public void setRecommend(Boolean recommend) {
        this.recommend = recommend;
    }

    @Override
    public String toString() {
        return "BlogQuery{" +
                "title='" + title + '\'' +
                ", typeId=" + typeId +
                ", recommend=" + recommend +
                '}';
    }
}
