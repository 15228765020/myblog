package com.my.blog.po;

import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "t_comment")
public class Comment implements Serializable {
    @Id
    @GeneratedValue
    private Long id;

    @NotEmpty(message = "用户昵称不能为空")
    private String nickname;

    @NotEmpty(message = "邮箱不能为空")
    @Pattern(regexp = "^([a-zA-Z]|[0-9])(\\w|\\-)+@[a-zA-Z0-9]+\\.([a-zA-Z]{2,4})$",message = "邮箱格式错误")
    private String email;

    @NotEmpty(message = "评论内容不能为空")
    @Length(max = 200,message = "评论内容在200字以内")
    private String content;

    private String avatar;
    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime;

//    private boolean isAdminComment;

    private boolean adminComment;
//    private Long parentCommentId; 这个我们使用了下面的的Comment自关联后，自动生成parentCommentId列
    //多个回复对应一个博客
    @ManyToOne
    private Blog blog;
    //自关联关系
    @OneToMany(mappedBy = "parentComment")
    private List<Comment> replyComments = new ArrayList<>();
    @ManyToOne
    private Comment parentComment;

    public Comment() {
    }

    public Comment(Long id, String nickname, String email, String content, String avatar, Date createTime) {
        this.id = id;
        this.nickname = nickname;
        this.email = email;
        this.content = content;
        this.avatar = avatar;
        this.createTime = createTime;
    }

    public boolean isAdminComment() {
        return adminComment;
    }

    public void setAdminComment(boolean adminComment) {
        this.adminComment = adminComment;
    }

    /*    public boolean isAdminComment() {
        return isAdminComment;
    }

    public void setAdminComment(boolean adminComment) {
        this.isAdminComment = adminComment;
    }*/


    public List<Comment> getReplyComments() {
        return replyComments;
    }

    public void setReplyComments(List<Comment> replyComments) {
        this.replyComments = replyComments;
    }

    public Comment getParentComment() {
        return parentComment;
    }

    public void setParentComment(Comment parentComment) {
        this.parentComment = parentComment;
    }

    public Blog getBlog() {
        return blog;
    }

    public void setBlog(Blog blog) {
        this.blog = blog;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "id=" + id +
                ", nickname='" + nickname + '\'' +
                ", email='" + email + '\'' +
                ", content='" + content + '\'' +
                ", avatar='" + avatar + '\'' +
                ", createTime=" + createTime +
                ", adminComment=" + adminComment +
                ", blog=" + blog +
                ", replyComments Size=" + replyComments +
                ", parentComment Id=" + parentComment +
                '}';
    }
}
