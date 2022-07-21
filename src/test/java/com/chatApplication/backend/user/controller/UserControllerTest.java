package com.chatApplication.backend.user.controller;

import com.chatApplication.backend.user.User;
import com.chatApplication.backend.user.service.UserService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.minidev.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import static org.junit.jupiter.api.Assertions.assertEquals;


@ExtendWith(MockitoExtension.class)
@WebMvcTest
class UserControllerTest {

    @InjectMocks
    UserController userController;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    UserService userService;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
    }

    @Test
    void shouldRegisterSuccessfully() throws Exception {

        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();

        User mockUser = new User(1l,"test","test");
        Mockito.when(userService.createUser(Mockito.any())).thenReturn(mockUser);


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
}