package com.chatApplication.backend.chat;

import com.chatApplication.backend.user.User;
import com.chatApplication.backend.user.exceptions.UsernameAlreadyExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;

@RestController
public class MessageController {
    @Autowired
    MessageService messageService;

    @PostMapping("/message")
    @ResponseStatus(code = HttpStatus.CREATED)
    Message sendMessage(@RequestBody Message message) {
        try {
            Message savedMessage = messageService.createMessage(message);
            return savedMessage;
        } catch (Throwable e) {
            System.out.println(e);
                throw new ResponseStatusException(
                        HttpStatus.INTERNAL_SERVER_ERROR, "Invalid Sender", e);
        }
    }

    @GetMapping("/messages")
    @ResponseStatus(code = HttpStatus.OK)
    ArrayList<Message> fetchAllMessagesWithGivenSenderAndReceiver(@RequestParam User sender,@RequestParam User receiver) {
            return messageService.findMessagesBySenderAndReceiver(sender,receiver);
        }
}
