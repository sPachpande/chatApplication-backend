package com.chatApplication.backend.chat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

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
}
