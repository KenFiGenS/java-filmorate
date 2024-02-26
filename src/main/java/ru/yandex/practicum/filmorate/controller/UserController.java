package ru.yandex.practicum.filmorate.controller;


import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.util.List;

@RestController
@RequestMapping("/users")
@Slf4j
@Validated
public class UserController extends BaseController<User> {

    private final UserService userService;

    public UserController(UserService userService) {
        super(userService);
        this.userService = userService;
    }

    @PostMapping
    public User create(@Valid @RequestBody User user) {
        validate(user);
        log.info("Creating user {}", user);
        return userService.create(user);
    }

    @PutMapping
    public User upDate(@Valid @RequestBody User user) {
        validate(user);
        log.info("Updating user {}", user);
        return userService.upDate(user);
    }

    @SneakyThrows
    @GetMapping("{id}")
    public User getById(@PathVariable int id) {
        log.info("Getting user by ID");
        return super.getById(id);
    }

    @SneakyThrows
    @GetMapping
    public List<User> getAll() {
        log.info("Getting all user");
        return super.getAll();
    }

    @SneakyThrows
    @PutMapping("{id}/friends/{friendId}")
    public void addFriend(@PathVariable int id, @PathVariable int friendId) {
        log.info("Add friend", friendId);
        userService.addFriend(id, friendId);
    }

    @SneakyThrows
    @DeleteMapping("{id}/friends/{friendId}")
    public void removeFriend(@PathVariable @Positive int id, @PathVariable @Positive int friendId) {
        log.info("Remove friend", friendId);
        userService.removeFriend(id, friendId);
    }

    @SneakyThrows
    @GetMapping("{id}/friends")
    public List<User> getAllFriends(@PathVariable @Positive int id) {
        log.info("Getting all friends");
        return userService.getAllFriends(id);
    }

    //
    @SneakyThrows
    @GetMapping("{id}/friends/common/{otherId}")
    public List<User> getGeneralFriends(@PathVariable @Positive int id, @PathVariable @Positive int otherId) {
        log.info("Getting general friends");
        return userService.getGeneralFriendsList(id, otherId);
    }

    @Override
    public void validate(User data) {
        if (data.getName() == null || data.getName().isBlank()) {
            log.warn("Поле\"Имя\" отсутствует.");
            data.setName(data.getLogin());
        }
    }
}
