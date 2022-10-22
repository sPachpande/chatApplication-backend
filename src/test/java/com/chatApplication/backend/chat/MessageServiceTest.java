package com.chatApplication.backend.chat;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

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
}

