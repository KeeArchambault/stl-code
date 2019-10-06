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
    private String body;

    @ManyToOne
    private Post post;

    public Comment() {

    }

    public Comment(String body) {
        this.body = body;
    }

    public int getId() {
        return id;
    }

    public Post getPost() {
        return post;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}