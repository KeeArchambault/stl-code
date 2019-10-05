package com.kee.stlcodecafe.models;

import javafx.beans.DefaultProperty;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Session {

    @Id
    @GeneratedValue
    private int Id;




    @OneToOne
    private User user;

    public Session() {

    }

    public Session(User user) {
        this.user = user;
    }

    public int getId() {
        return Id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int userId(){
        return this.user.getId();
    }

}
