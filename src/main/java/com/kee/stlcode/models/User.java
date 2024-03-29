package com.kee.stlcode.models;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.nio.file.Paths;
import java.util.List;

@Entity
public class User {

    @Id
    @GeneratedValue
    private int id;

    @NotNull
    @Size(min = 3, max = 15, message = "Name length must be between 3 and 15 characters.")
    private String name;

    @Email
    @NotNull
    private String email;

    @NotNull
    @Size(min = 3, max = 15, message = "Password length must be between 3 and 15 characters.")
    private String password;

    @NotNull
    private String verify;

    @OneToMany
    private List<Post> posts;

    private File profilePic;

    @OneToMany
    private List<Comment> comments;

    public User() {
    }

    public User(String name) {
    }

    public File getProfilePic() {
        return profilePic;
    }

    public void write(MultipartFile file, Path dir) {
    }

    public void setProfilePic(MultipartFile profilePic, Path dir) throws IOException {

        Path filepath = Paths.get(dir.toString());
        profilePic.transferTo(filepath);

        this.profilePic = filepath.toFile();
    }

    public void addPost(Post post) {
        posts.add(post);
    }
    public List<Post> getPosts() {
        return posts;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    public String getVerify() {
        return verify;
    }
    public void setVerify(String verify) {
        this.verify = verify;
    }

    public List<Comment> getComments() {
        return comments;
    }
    public void addComment(Comment comment) {
        this.comments.add(comment);
    }

}
