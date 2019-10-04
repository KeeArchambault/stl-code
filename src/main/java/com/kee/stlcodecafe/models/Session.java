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

    private int sessionKey;

    public Session(){

    }

    public Session(int id){
        this.setSessionKey(id);
    }

    public int getSessionKey() {
        return sessionKey;
    }

    public void setSessionKey(int sessionKey) {
        this.sessionKey = sessionKey;
    }
    

    public int getId() {
        return Id;
    }
}
