package com.kee.stlcode.models;


import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.transaction.Transactional;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Entity
public class Post implements Comparable<Post>{

    @Id
    @GeneratedValue
    private int id;

    @NotNull
    @Size(min = 3, max = 50)
    private String title;

    @NotNull
    @Column(length = 16000000)
    @Size(min = 1, max =1500, message = "Post length must be between 1 and 1500 characters.")
    private String body;

    @ManyToOne
    private User user;

    private boolean deleted = false;

    @OneToMany
    private List<Comment> comments;

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

    @Transactional
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    @Transactional
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

    public boolean isDeleted() {
        return deleted;
    }
    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }
    public List<Comment> getComments() {
        return comments;
    }
    public void addComment(Comment comment){
        this.comments.add(comment);
    }

    public String getCreated() {
        String formattedDate = new SimpleDateFormat("MM/dd/yyyy, h:mm a").format(created);
        return formattedDate;
    }

    @Override
    public int compareTo(Post post) {
        if (getCreated() == null || post.getCreated() == null) {
            return 0;
        }
        return getCreated().compareTo(post.getCreated());
    }
}


