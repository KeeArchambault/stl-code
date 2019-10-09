package com.kee.stlcodecafe.models;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

@Entity
public class Message {

    @Id
    @GeneratedValue
    private int id;

    @OneToOne
    @NotNull
    private User recipient;

    @OneToOne
    @NotNull
    private User sender;

    @NotNull
    @Size(min=3, max=15)
    private String subject;

    @Lob
    @NotNull
    @Size(min=1, max = 500)
    private String body;


    @DateTimeFormat()
    private Date created;

    @PrePersist
    protected void onCreate() {
        created = new Date();
    }

    public int getId() {
        return id;
    }

    public Message(){
    }

    public Message(String subject, String body, User recipient, User sender){
    }

    public User getRecipient() {
        return recipient;
    }

    public void setRecipient(User recipient) {
        this.recipient = recipient;
    }

    public User getSender() {
        return sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Date getCreated() {
        return created;
    }
}
