package com.kee.stlcodecafe.models;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

@Entity
public class Comment {

    @Id
    @GeneratedValue
    private int id;

    @NotNull
    @Lob
    @Size(min = 1, max = 500, message = "Comment too long.")
    private String commentBody;

    @ManyToOne
    private Post post;


    @ManyToOne
    User user;

    @DateTimeFormat()
    private Date created;

    @PrePersist
    protected void onCreate() {
        created = new Date();
    }

    public Comment() {

    }

    public Comment(String commentBody) {
        this.commentBody = commentBody;
    }

    public int getId() {
        return id;
    }

    public Post getPost() {
        return post;
    }

    public String getCommentBody() {
        return commentBody;
    }

    public void setCommentBody(String commentBody) {
        this.commentBody = commentBody;
    }

    public User getUser() {
        return user;
    }

    public Date getCreated() {
        return created;
    }
}