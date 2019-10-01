package com.kee.stlcodecafe.models;

import javax.persistence.*;


import javax.persistence.Entity;
        import javax.persistence.Id;
        import javax.persistence.GeneratedValue;

@Entity
public class Session {

    @Id
    @GeneratedValue
    private int id;

    @OneToOne
    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

}