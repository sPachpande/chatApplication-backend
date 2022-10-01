package com.chatApplication.backend.chat.service;

import com.chatApplication.backend.chat.model.Message;
import com.chatApplication.backend.chat.model.MessageType;
import com.corundumstudio.socketio.SocketIOClient;
import org.springframework.stereotype.Service;

@Service
public class SocketService {

    public void sendMessage(String room,String eventName, SocketIOClient senderClient, String message) {
        for (
                SocketIOClient client : senderClient.getNamespace().getRoomOperations(room).getClients()) {
            if (!client.getSessionId().equals(senderClient.getSessionId())) {
                client.sendEvent(eventName,
                        new Message(MessageType.SERVER, message));
            }
        }
    }

}