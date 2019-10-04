package com.kee.stlcodecafe.models.forms;

import com.kee.stlcodecafe.models.Post;
import com.kee.stlcodecafe.models.User;

import javax.validation.constraints.NotNull;

public class AddPostForm {

    @NotNull
    private int userId;

    @NotNull
    private int postId;

    private User user;

    private Iterable<Post> posts;

    public int getUserId() {
        return userId;
    }

    public int getPostId(){
        return postId;
    }

    public User getUser(){
        return user;
    }

    public Iterable<Post> getPosts() {
        return posts;
    }

    public AddPostForm(){
    }

    public AddPostForm(User menu, Iterable<Post> posts){
    }
}

