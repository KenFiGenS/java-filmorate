package ru.yandex.practicum.filmorate.controller;


import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.BaseService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/users")
@Slf4j
public class UserController extends BaseController<User> {

    public UserController(BaseService<User> baseService) {
        super(baseService);
    }

    @SneakyThrows
    @GetMapping
    public List<User> getAll() {
        log.info("Getting all user");
        return super.getAll();
    }

    @SneakyThrows
    @GetMapping("{id}")
    public User getById(@PathVariable int id) {
        log.info("Getting user by ID");
        return super.getById(id);
    }

    @SneakyThrows
    @GetMapping("{id}/friends")
    public List<User> getAllFriends(@PathVariable int id) {
        log.info("Getting all friends");
        return getUserService().getAllFriends(id);
    }

    @SneakyThrows
    @GetMapping("{id}/friends/common/{otherId}")
    public List<User> getGeneralFriends(@PathVariable int id, @PathVariable int otherId) {
        log.info("Getting general friends");
        return getUserService().generalFriendsList(id, otherId);
    }

    @PostMapping
    public User create(@Valid @RequestBody User user) {
        validate(user);
        log.info("Creating user {}", user);
        return super.create(user);
    }

    @PutMapping
    public User upDate(@Valid @RequestBody User user) {
        validate(user);
        log.info("Updating user {}", user);
        return super.upDate(user);
    }

    @SneakyThrows
    @PutMapping("{id}/friends/{friendId}")
    public void addFriend(@PathVariable int id, @PathVariable int friendId) {
        log.info("Add friend", friendId);
        getUserService().addFriend(id, friendId);
    }

    @SneakyThrows
    @DeleteMapping("{id}/friends/{friendId}")
    public void removeFriend(@PathVariable int id, @PathVariable int friendId) {
        log.info("Remove friend", friendId);
        getUserService().removeFriend(id, friendId);
    }

    @Override
    public void validate(User data) {
        if (data.getName() == null || data.getName().isBlank()) {
            log.warn("Поле\"Имя\" отсутствует.");
            data.setName(data.getLogin());
        }
    }
}
