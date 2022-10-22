package com.chatApplication.backend.chat;

import com.chatApplication.backend.user.User;
import com.chatApplication.backend.user.repository.UserRepository;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.minidev.json.JSONObject;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.annotation.DirtiesContext.ClassMode.AFTER_CLASS;

@ExtendWith(MockitoExtension.class)
@WebMvcTest
@DirtiesContext(classMode = AFTER_CLASS)
@Disabled
public class MessageControllerTest {

    @InjectMocks
    MessageController messageController;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    UserRepository userRepository;

    @MockBean
    MessageService messageService;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(messageController).build();
    }

    @Test
    void shouldSaveMessageSuccessfully() throws Exception{

        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();

        User mockSender = new User(1l,"test1","test1");
        User mockReceiver = new User(2l,"test2","test2");
//        userService.createUser(mockSender);
//        userService.createUser(mockReceiver);
        Message mockMessage = new Message(1l,mockSender,mockReceiver,"Hello");
        when(messageService.createMessage(Mockito.any())).thenReturn(mockMessage);

        JSONObject sender = new JSONObject();
        sender.put("id",1);
        JSONObject receiver = new JSONObject();
        sender.put("id",2);
        JSONObject requestBody = new JSONObject();
        requestBody.put("sender", sender);
        requestBody.put("password", receiver);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/message")
                .accept(MediaType.APPLICATION_JSON)
                .content(String.valueOf(requestBody))
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        Message savedMessage = gson.fromJson(result.getResponse().getContentAsString(),Message.class);

        assertEquals(HttpStatus.CREATED.value(), result.getResponse().getStatus());
        assertEquals(mockMessage.toString(), savedMessage.toString());
    }
}
