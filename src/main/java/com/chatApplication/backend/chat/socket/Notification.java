package com.chatApplication.backend.chat.socket;

public class Notification {
    private String senderId;
    private String room;

    public Notification() {
    }
    public Notification(String senderId) {
        this.senderId = senderId;
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }
}
