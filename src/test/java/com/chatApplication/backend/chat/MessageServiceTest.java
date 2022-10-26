package com.chatApplication.backend.chat;

import com.chatApplication.backend.user.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class MessageServiceTest {

    @InjectMocks
    MessageService messageService;
    @Mock
    MessageRepository messageRepository;

    @Test
    void shouldBeAbleToCreateMessage() throws Exception {
        Message mockMessage = mock(Message.class);
        Mockito.when(messageRepository.save(Mockito.any())).thenReturn(mockMessage);

        Message savedMessage = messageService.createMessage(mockMessage);

        verify(messageRepository).save(mockMessage);
        assertThat(mockMessage, is(equalTo(savedMessage)));
    }
    @Test
    void shouldReturnAllMessagesWithGivenSenderAndReceiver(){

        User sender= new User(1l,"test1","test1");
        User receiver= new User(2l,"test2","test2");

        Message message1 = new Message(1l,sender,receiver,"This is a message");

        ArrayList<Message> messagesList = new ArrayList<Message>();
        messagesList.add(message1);

        Mockito.when(messageRepository.findMessageBySenderAndReceiver(sender,receiver)).thenReturn(messagesList);

        ArrayList<Message> savedMessages = messageService.findMessagesBySenderAndReceiver(sender,receiver);

        assertThat(savedMessages.toString(), is(equalTo(messagesList.toString())));
    }
}

