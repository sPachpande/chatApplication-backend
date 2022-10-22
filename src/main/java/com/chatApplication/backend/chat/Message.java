package com.chatApplication.backend.chat;

import com.chatApplication.backend.user.User;

import javax.persistence.*;

@Entity
@Table(name = "Messages")
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    @OneToOne()
    @JoinColumn(name = "sender", referencedColumnName = "id", nullable = false)
    private User sender;

    @OneToOne()
    @JoinColumn(name = "receiver", referencedColumnName = "id", nullable = false)
    private User receiver;

    private String data;

    public Message() {
    }

    public Message(Long id, User sender, User receiver, String data) {
        this.id = id;
        this.sender = sender;
        this.receiver = receiver;
        this.data = data;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getSender() {
        return sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    public User getReceiver() {
        return receiver;
    }

    public void setReceiver(User receiver) {
        this.receiver = receiver;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "Message{" +
                "id=" + id +
                ", sender=" + sender +
                ", receiver=" + receiver +
                ", data='" + data + '\'' +
                '}';
    }
}
