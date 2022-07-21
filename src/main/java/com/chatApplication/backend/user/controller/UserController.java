package com.chatApplication.backend.user.controller;

import com.chatApplication.backend.user.User;
import com.chatApplication.backend.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    @Autowired
    UserService userService;

    @PostMapping("/users")
    @ResponseStatus(code = HttpStatus.CREATED)
    User register(@RequestBody User user) {

        User savedUser = userService.createUser(user);
        return savedUser;
    }
}
