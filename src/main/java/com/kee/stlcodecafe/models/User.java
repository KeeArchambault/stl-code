package com.kee.stlcodecafe.models;





import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
public class User {

    @NotNull
    @Size(min=3, max=15)
    private String userName;

    @Id
    @GeneratedValue
    private int id;

    @OneToMany
    private List<Post> posts;

    public User(){
    }

    public User(String name){
    }

    public void addPost(Post post){
        posts.add(post);
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public List<Post> getPosts() {
        return posts;
    }

    public int getId() {
        return id;
    }

}
