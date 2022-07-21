package com.chatApplication.backend.user.service;

import com.chatApplication.backend.user.User;
import com.chatApplication.backend.user.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @InjectMocks
    UserService userService;
    @Mock
    UserRepository userRepository;

    @Test
    void shouldBeAbleToCreateUser() {
        User mockUser = mock(User.class);
        Mockito.when(userRepository.save(Mockito.any())).thenReturn(mockUser);
        User user = new User(1L,"test", "test");

        User savedUser = userService.createUser(user);

        verify(userRepository).save(user);
        assertThat(mockUser, is(equalTo(savedUser)));
    }
}