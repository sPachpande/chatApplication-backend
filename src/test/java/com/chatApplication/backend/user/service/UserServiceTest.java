package com.chatApplication.backend.user.service;

import com.chatApplication.backend.user.ApplicationUser;
import com.chatApplication.backend.user.User;
import com.chatApplication.backend.user.exceptions.UsernameAlreadyExistsException;
import com.chatApplication.backend.user.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Optional;

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
    @Test
    void shouldReturnListOfAllUsers(){
        User user1 = mock(User.class);
        User user2 = mock(User.class);
        ArrayList<User> usersList = new ArrayList<User>();
        usersList.add(user1);
        usersList.add(user2);
        Mockito.when(userRepository.findAll()).thenReturn(usersList);

        ArrayList<User> savedUsers = userService.fetchAllUsers();

        assertThat(savedUsers.toString(), is(equalTo(usersList.toString())));
    }

    @Test
    void shouldBeAbleToLoadUserByUsernameIfUserExists(){
        User user= new User(1l,"test","test");
        Mockito.when(userRepository.findUserByUsername(Mockito.any())).thenReturn(Optional.of(user));

        ApplicationUser appUser = (ApplicationUser) userService.loadUserByUsername("test");

        assertThat(appUser.getUsername(),is(equalTo("test")));
    }
    @Test
    void shouldNotBeAbleToLoadUserByUsernameIfUserDoesNotExists(){
        Mockito.when(userRepository.findUserByUsername(Mockito.any())).thenReturn(Optional.empty());

        ApplicationUser appUser = (ApplicationUser) userService.loadUserByUsername("test");

        assertThat(appUser,is(equalTo(null)));
    }
}
