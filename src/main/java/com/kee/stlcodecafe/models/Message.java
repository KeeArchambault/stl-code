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

    @ManyToMany(mappedBy = "receivedMessages")
    private List<User> recipients;

    @ManyToMany(mappedBy = "sentMessages")
    private List<User> senders;

    @NotNull
    @Size(min=1, max=15)
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

    public Message(String subject, String body){
    }

    public List<User> getRecipients() {
        return recipients;
    }

    public List<User> getSenders() {
        return senders;
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
