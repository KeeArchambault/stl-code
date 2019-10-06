package com.kee.stlcodecafe.models;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

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
}