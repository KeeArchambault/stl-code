package com.kee.stlcode.models;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.text.SimpleDateFormat;
import java.util.Date;

@Entity
public class Message {

    @Id
    @GeneratedValue
    private int id;

    @NotNull
    @Size(min = 1, max = 50)
    private String subject;

    @Lob
    @NotNull
    @Size(min = 1, max = 1500)
    private String body;

    @ManyToOne
    private User recipient;

    @ManyToOne
    private User sender;

    private boolean senderDeleted = false;

    private boolean recipientDeleted = false;

    @DateTimeFormat()
    private Date created;

    @PrePersist
    protected void onCreate() {
        created = new Date();
    }

    public Message() {
    }

    public Message(String subject, String body) {
    }

    public int getId() {
        return id;
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

    public String getCreated() {
        return new SimpleDateFormat("MM/dd/yyyy, h:mm a").format(created);
    }

    public boolean isSenderDeleted() {
        return senderDeleted;
    }
    public void setSenderDeleted(boolean senderDeleted) {
        this.senderDeleted = senderDeleted;
    }

    public boolean isRecipientDeleted() {
        return recipientDeleted;
    }
    public void setRecipientDeleted(boolean recipientDeleted) {
        this.recipientDeleted = recipientDeleted;
    }
}
