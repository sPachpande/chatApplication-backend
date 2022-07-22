package com.chatApplication.backend.user.controller;

import com.chatApplication.backend.user.User;
import com.chatApplication.backend.user.exceptions.UsernameAlreadyExistsException;
import com.chatApplication.backend.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;

@RestController
public class UserController {
    @Autowired
    UserService userService;

    @PostMapping("/users")
    @ResponseStatus(code = HttpStatus.CREATED)
    User register(@RequestBody User user) {
        try {
            User savedUser = userService.createUser(user);
            return savedUser;
        } catch (Throwable e) {
            if (e instanceof UsernameAlreadyExistsException) {
                throw new ResponseStatusException(
                        HttpStatus.CONFLICT, "Username already exists", e);
            } else {
                throw new ResponseStatusException(
                        HttpStatus.INTERNAL_SERVER_ERROR, "Something is wrong with the system", e);
            }
        }
    }

    @GetMapping("/users")
    @ResponseStatus(code = HttpStatus.OK)
    ArrayList<User> fetchAllUsers(){
        return userService.fetchAllUsers();
    }
}
