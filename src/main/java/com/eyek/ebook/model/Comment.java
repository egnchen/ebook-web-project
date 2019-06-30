package com.eyek.ebook.model;

import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Date;

public class Comment {

    @Field("rspsr_id")
    private Integer userId;

    // redundancy - for performance
    @Field("rspsr_name")
    private String username;

    @Field("rsp_content")
    private String content;

    @Field("rsp_time")
    private Date createTime;

    @Field("replies")
    private Comment[] replies;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setUser(User user) {
        this.userId = user.getId();
        this.username = user.getUsername();
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Comment[] getReplies() {
        return replies;
    }

    public void setReplies(Comment[] replies) {
        this.replies = replies;
    }
}
