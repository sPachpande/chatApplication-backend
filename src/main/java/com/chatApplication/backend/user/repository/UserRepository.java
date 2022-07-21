package com.chatApplication.backend.user.repository;

import com.chatApplication.backend.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}