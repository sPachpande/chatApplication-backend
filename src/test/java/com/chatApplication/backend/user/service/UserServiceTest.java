package com.chatApplication.backend.user.service;

import com.chatApplication.backend.user.User;
import com.chatApplication.backend.user.exceptions.UsernameAlreadyExistsException;
import com.chatApplication.backend.user.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @InjectMocks
    UserService userService;
    @Mock
    UserRepository userRepository;

    @Test
    void shouldBeAbleToCreateUser() throws UsernameAlreadyExistsException, Exception {
        User mockUser = mock(User.class);
        Mockito.when(userRepository.save(Mockito.any())).thenReturn(mockUser);

        User savedUser = userService.createUser(mockUser);

        verify(userRepository).save(mockUser);
        assertThat(mockUser, is(equalTo(savedUser)));
    }

    @Test
    void shouldThrowUsernameAlreadyExistsExceptionIfUsernameAlreadyExists(){
        User mockUser = mock(User.class);
        Mockito.when(userRepository.save(Mockito.any())).thenReturn(new Exception());

        assertThrows(UsernameAlreadyExistsException.class, () -> userService.createUser(mockUser));

    }
}