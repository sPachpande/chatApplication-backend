package com.chatApplication.backend.chat.socket;

import com.chatApplication.backend.chat.socket.Notification;
import com.chatApplication.backend.chat.socket.SocketService;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.listener.ConnectListener;
import com.corundumstudio.socketio.listener.DataListener;
import com.corundumstudio.socketio.listener.DisconnectListener;
import com.corundumstudio.socketio.namespace.Namespace;
import com.corundumstudio.socketio.namespace.NamespacesHub;

import org.springframework.stereotype.Component;

@Component
public class SocketModule {


    private final SocketIOServer server;
    private final SocketService socketService;

    public SocketModule(SocketIOServer server, SocketService socketService) {
        this.server = server;
        this.socketService = socketService;
        server.addConnectListener(onConnected());
        server.addDisconnectListener(onDisconnected());
        server.addEventListener("send_notification", Notification.class, onChatReceived());
    }


    private DataListener<Notification> onChatReceived() {

        return (senderClient, data, ackSender) -> {

            socketService.sendMessage(data.getRoom(), "get_notification", senderClient, data.getSenderId());
        };
    }


    private ConnectListener onConnected() {

        return (client) -> {
            String room = client.getHandshakeData().getSingleUrlParam("room");
            client.joinRoom(room);
        };

    }

    private DisconnectListener onDisconnected() {
        return client -> {
            String room = client.getHandshakeData().getSingleUrlParam("room");
            client.leaveRoom(room);
        };
    }

}