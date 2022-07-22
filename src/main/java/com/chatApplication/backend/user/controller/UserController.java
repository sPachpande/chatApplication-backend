package com.chatApplication.backend.user.controller;

import com.chatApplication.backend.user.User;
import com.chatApplication.backend.user.exceptions.UsernameAlreadyExistsException;
import com.chatApplication.backend.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
public class UserController {
    @Autowired
    UserService userService;

    @PostMapping("/users")
    @ResponseStatus(code = HttpStatus.CREATED)
    User register(@RequestBody User user){
        try {
            User savedUser = userService.createUser(user);
            return savedUser;
        }
        catch(Throwable e){
            if(e instanceof UsernameAlreadyExistsException){
                throw new ResponseStatusException(
                          HttpStatus.CONFLICT, "Username already exists", e);
            }
            else {
                throw new ResponseStatusException(
                        HttpStatus.INTERNAL_SERVER_ERROR, "Something is wrong with the system", e);
            }
        }
    }
}
