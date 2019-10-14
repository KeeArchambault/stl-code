package com.kee.stlcode.models;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

@Entity
public class Comment implements Comparable<Comment>{

    @Id
    @GeneratedValue
    private int id;

    @NotNull
    @Size(min = 1, max = 1500, message = "Comment too long.")
    private String commentBody;

    @ManyToOne
    private Post post;

    @ManyToOne
    private User user;

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

    public String getCommentBody() {
        return commentBody;
    }
    public void setCommentBody(String commentBody) {
        this.commentBody = commentBody;
    }

    public Post getPost() {
        return post;
    }
    public void setPost(Post post) {
        this.post = post;
    }

    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    }

    public Date getCreated() {
        return created;
    }

    @Override
    public int compareTo(Comment comment) {
        if (getCreated() == null || comment.getCreated() == null) {
            return 0;
        }
        return getCreated().compareTo(comment.getCreated());
    }
}
