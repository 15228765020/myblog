package com.my.blog.po;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "t_visitor_records")
public class VisitorRecords {
    @Id
    @GeneratedValue

    private Integer id;

    private String visitorAddress;

    private String visitorIp;

    private Date lastVisitTime;

    private Integer totalVisitNumber;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getVisitorAddress() {
        return visitorAddress;
    }

    public void setVisitorAddress(String visitorAddress) {
        this.visitorAddress = visitorAddress;
    }

    public String getVisitorIp() {
        return visitorIp;
    }

    public void setVisitorIp(String visitorIp) {
        this.visitorIp = visitorIp;
    }

    public Date getLastVisitTime() {
        return lastVisitTime;
    }

    public void setLastVisitTime(Date lastVisitTime) {
        this.lastVisitTime = lastVisitTime;
    }

    public Integer getTotalVisitNumber() {
        return totalVisitNumber;
    }

    public void setTotalVisitNumber(Integer totalVisitNumber) {
        this.totalVisitNumber = totalVisitNumber;
    }
}
