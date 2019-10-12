package com.kee.stlcodecafe.models;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;

@Entity
public class Message {

    @Id
    @GeneratedValue
    private int id;

    @ManyToOne
    private User recipient;

    @ManyToOne
    private User sender;

    @NotNull
    @Size(min=1, max=50)
    private String subject;

    @Lob
    @NotNull
    @Size(min=1, max = 1500)
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

    public Message(String subject, String body){
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

}
