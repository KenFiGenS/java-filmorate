package ru.yandex.practicum.filmorate.storageTest;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.UserDbStorage;

import java.sql.Date;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@JdbcTest
@RequiredArgsConstructor(onConstructor_ = @Autowired)
class UserDbStorageTest {
    private final JdbcTemplate jdbcTemplate;
    private UserDbStorage userDbStorage;

    @BeforeEach
    void setUp() {
        userDbStorage = new UserDbStorage(jdbcTemplate);
    }

    @Test
    public void testFindUserById() {
        User newUser = new User(1, "user@email.ru", "vanya123", "Ivan Petrov", Date.valueOf("1990-1-1"));
        userDbStorage.create(newUser);

        User savedUser = userDbStorage.getById(1);

        assertThat(savedUser)
                .isNotNull()
                .usingRecursiveComparison()
                .isEqualTo(newUser);
    }

    @Test
    public void testFindAllUser() {
        User newUser2 = new User(2, "user2@email.ru", "Kolya", "Kolya Petrov", Date.valueOf("1990-1-2"));
        User newUser = new User(1, "user@email.ru", "vanya123", "Ivan Petrov", Date.valueOf("1990-1-1"));
        userDbStorage.create(newUser);
        userDbStorage.create(newUser2);
        List<User> users = List.of(newUser, newUser2);

        List<User> savedUsers = userDbStorage.getAll();

        assertThat(savedUsers.stream().allMatch(u -> users.contains(u)));
    }

    @Test
    public void testUpdateUser() {
        User newUser2 = new User(1, "user2@email.ru", "Kolya", "Kolya Petrov", Date.valueOf("1990-1-2"));
        userDbStorage.create(newUser2);
        User updateUser = new User(1, "user@email.ru", "vanya123", "Ivan Petrov", Date.valueOf("1990-1-1"));
        userDbStorage.upDate(updateUser);

        User savedUser = userDbStorage.getById(1);

        // проверяем утверждения
        assertThat(savedUser)
                .isNotNull()
                .usingRecursiveComparison()
                .isEqualTo(updateUser);
    }
}
