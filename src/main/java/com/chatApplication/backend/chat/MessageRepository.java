package com.chatApplication.backend.chat;

import com.chatApplication.backend.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;


@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {
   @Query(value = "SELECT * FROM messages WHERE ((sender=:sender AND receiver=:receiver) OR (sender=:receiver AND receiver=:sender))", nativeQuery = true)
   ArrayList<Message> findMessageBySenderAndReceiver(User sender, User receiver);
}
