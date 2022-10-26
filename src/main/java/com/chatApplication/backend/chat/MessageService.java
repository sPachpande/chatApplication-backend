package com.chatApplication.backend.chat;

import com.chatApplication.backend.user.ApplicationUser;
import com.chatApplication.backend.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

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

    public ArrayList<Message> findMessagesBySenderAndReceiver(User sender,User receiver) {
        ArrayList<Message> messages = messageRepository.findMessageBySenderAndReceiver(sender, receiver);
        return messages;
    }
}
