package com.chatApplication.backend.chat.model;



public class Message {
    private MessageType type;
    private String message;
    private String room;

    public Message() {
    }

    public MessageType getType() {
        return type;
    }

    public void setType(MessageType type) {
        this.type = type;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public Message(MessageType type, String message) {
        this.type = type;
        this.message = message;
    }
}
