package com.kee.stlcodecafe.models;

import javax.persistence.*;


import javax.persistence.Entity;
        import javax.persistence.Id;
        import javax.persistence.GeneratedValue;
import javax.validation.constraints.NotNull;
        import javax.validation.constraints.Size;
import java.util.List;

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