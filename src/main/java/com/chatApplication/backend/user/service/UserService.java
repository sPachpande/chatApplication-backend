package com.chatApplication.backend.user.service;

import com.chatApplication.backend.user.ApplicationUser;
import com.chatApplication.backend.user.User;
import com.chatApplication.backend.user.exceptions.UsernameAlreadyExistsException;
import com.chatApplication.backend.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;


@Service
public class UserService{
    @Autowired
    UserRepository userRepository;

    public User createUser(User user) throws UsernameAlreadyExistsException{
        try {
            User savedUser = userRepository.save(user);
            return user;
        } catch (Throwable e) {
            throw new UsernameAlreadyExistsException();
        }
    }

    public ArrayList<User> fetchAllUsers() {
        return (ArrayList<User>) userRepository.findAll();
    }
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findUserByUsername(username);
        if(user.isPresent())
        {
            return new ApplicationUser(user.get());
        }
        else
            return null;
    }

}
