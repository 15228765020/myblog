package com.my.blog.vo;

public class VisitorRecordsVo {

    //地址
    private String adreess;

    //ip地址
    private String ipAdr;

    //访问次数
    private Integer visitCount;

    @Override
    public String toString() {
        return "VisitorRecordsVo{" +
                "adreess='" + adreess + '\'' +
                ", ipAdr='" + ipAdr + '\'' +
                ", visitCount=" + visitCount +
                '}';
    }

    public String getAdreess() {
        return adreess;
    }

    public void setAdreess(String adreess) {
        this.adreess = adreess;
    }

    public String getIpAdr() {
        return ipAdr;
    }

    public void setIpAdr(String ipAdr) {
        this.ipAdr = ipAdr;
    }

    public Integer getVisitCount() {
        return visitCount;
    }

    public void setVisitCount(Integer visitCount) {
        this.visitCount = visitCount;
    }
}
