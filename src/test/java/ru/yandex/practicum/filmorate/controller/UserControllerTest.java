package ru.yandex.practicum.filmorate.controller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;
import ru.yandex.practicum.filmorate.storage.UserDbStorage;

import java.sql.Date;

class UserControllerTest {

    private UserController userController;

    @BeforeEach
    void setUp() {
        userController = new UserController(new UserService(new UserDbStorage(new JdbcTemplate())));
    }

    @Test
    void validate() {
        User user = User.builder()
                .email("sng.inc@yandex.ru")
                .login("KenGa")
                .name("Hennady")
                .birthday(new Date(1988, 11, 24))
                .build();
        userController.validate(user);
        Assertions.assertEquals("Hennady", user.getName());
    }

    @Test
    void validateNegative() {
        User user = User.builder()
                .email("sng.inc@yandex.ru")
                .login("KenGa")
                .name(null)
                .birthday(new Date(1988, 11, 24))
                .build();
        userController.validate(user);
        Assertions.assertEquals(user.getLogin(), user.getName());
    }
}
