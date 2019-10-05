package com.kee.stlcodecafe.models;


import org.springframework.data.annotation.CreatedDate;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

@Entity
public class Post {

    @Id
    @GeneratedValue
    private int id;

    @NotNull
    @Size(min = 3, max = 15)
    private String title;

    @NotNull
    @Lob
    @Size(min = 1, max =500, message = "Post too long.")
    private String body;

    @ManyToOne
    private User user;

    public Date getCreated() {
        return created;
    }

    @DateTimeFormat()
    private Date created;

    @PrePersist
    protected void onCreate() {
        created = new Date();
    }

    public Post() {
    }

    public Post(String title, String body){
        this.title = title;
        this.body = body;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
