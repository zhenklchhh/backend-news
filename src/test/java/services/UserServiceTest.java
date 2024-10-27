package services;

import dao.UserDao;
import models.User;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceTest {

    private final UserDao userDao = Mockito.mock(UserDao.class);
    private final UserService userService = new UserService(userDao);

    @Test
    void createUser() {
        User user = new User("Test User", "test@example.com", "password");
        when(userDao.createUser(user)).thenReturn(user);

        User createdUser = userService.createUser(user);

        assertEquals(user, createdUser);
        verify(userDao, times(1)).createUser(user);
    }

    @Test
    void getAllUsers() {
        List<User> users = new ArrayList<>();
        users.add(new User("User 1", "user1@example.com", "password"));
        users.add(new User("User 2", "user2@example.com", "password"));

        when(userDao.getAllUsers()).thenReturn(users);

        List<User> retrievedUsers = userService.getAllUsers();

        assertEquals(users, retrievedUsers);
        verify(userDao, times(1)).getAllUsers();
    }

    @Test
    void getUserById() {
        int userId = 1;
        User user = new User(userId, "Test User", "test@example.com", "password");

        when(userDao.getUser(userId)).thenReturn(user);

        User retrievedUser = userService.getUserById(userId);

        assertEquals(user, retrievedUser);
        verify(userDao, times(1)).getUser(userId);
    }

    @Test
    void getUserByIdNotFound() {
        int userId = 1;

        when(userDao.getUser(userId)).thenReturn(null);

        User retrievedUser = userService.getUserById(userId);

        assertNull(retrievedUser);
        verify(userDao, times(1)).getUser(userId);
    }

    @Test
    void updateUser() {
        User user = new User(1, "Test User", "test@example.com", "password");

        userService.updateUser(user);

        verify(userDao, times(1)).updateUser(user);
    }

    @Test
    void deleteUser() {
        int userId = 1;

        userService.deleteUser(userId);

        verify(userDao, times(1)).deleteUser(userId);
    }
}