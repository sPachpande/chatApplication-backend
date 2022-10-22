package com.chatApplication.backend.chat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MessageService {

    @Autowired
    MessageRepository messageRepository;

    public Message createMessage(Message message) throws Exception {
        try {
            Message savedMessage = messageRepository.save(message);
            return message;
        } catch (Throwable e) {
            throw new Exception();
        }
    }
}
