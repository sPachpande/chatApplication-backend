package com.chatApplication.backend.user.controller;

import com.chatApplication.backend.chat.MessageRepository;
import com.chatApplication.backend.user.User;
import com.chatApplication.backend.user.exceptions.UsernameAlreadyExistsException;
import com.chatApplication.backend.user.repository.UserRepository;
import com.chatApplication.backend.user.service.UserService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.minidev.json.JSONObject;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.ClassOrderer.OrderAnnotation;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.annotation.DirtiesContext.ClassMode.AFTER_CLASS;
import static org.springframework.test.annotation.DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD;


@ExtendWith(MockitoExtension.class)
@WebMvcTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
class UserControllerTest {

    @InjectMocks
    UserController userController;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    UserService userService;

    @MockBean
    MessageRepository messageRepository;

    @AfterAll
    public static void cleanup(){

    }
    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
    }

    @Test
    void shouldRegisterSuccessfully() throws UsernameAlreadyExistsException, Exception {

        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();

        User mockUser = new User(1l,"test","test");
        when(userService.createUser(Mockito.any())).thenReturn(mockUser);


        JSONObject requestBody = new JSONObject();
        requestBody.put("username", "test");
        requestBody.put("password", "test");

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/users")
                .accept(MediaType.APPLICATION_JSON)
                .content(String.valueOf(requestBody))
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        User savedUser = gson.fromJson(result.getResponse().getContentAsString(),User.class);
        assertEquals(HttpStatus.CREATED.value(), result.getResponse().getStatus());
        assertEquals(mockUser.toString(), savedUser.toString());
    }

    @Test
    void shouldNotBeAbleToRegisterIfUsernameAlreadyExists() throws UsernameAlreadyExistsException, Exception {

        when(userService.createUser(Mockito.any())).thenThrow(new UsernameAlreadyExistsException());

        JSONObject requestBody = new JSONObject();
        requestBody.put("username", "test");
        requestBody.put("password", "test");

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/users")
                .accept(MediaType.APPLICATION_JSON)
                .content(String.valueOf(requestBody))
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        assertEquals(HttpStatus.CONFLICT.value(), result.getResponse().getStatus());
    }

    @Test
    void shouldBeAbleToFetchAllUsers() throws Exception {
        User user1 = new User(1l,"test1","test1");//Do not use mock here as it has to go through proper jackson deserialization
        User user2 = new User(2l,"test2","test2");
        ArrayList<User> usersList = new ArrayList<User>();
        usersList.add(user1);
        usersList.add(user2);
        when(userService.fetchAllUsers()).thenReturn(usersList);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/users")
                .accept(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());
    }

}