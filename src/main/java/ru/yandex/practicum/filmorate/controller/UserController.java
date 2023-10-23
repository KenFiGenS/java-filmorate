package ru.yandex.practicum.filmorate.controller;


import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.User;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/users")
@Slf4j
public class UserController extends BaseController<User> {


    @SneakyThrows
    @GetMapping
    public List<User> getAll() {
        log.info("Getting all user");
        return super.getAll();
    }

    @SneakyThrows
    @PostMapping
    public User create(@Valid @RequestBody User user)  {
        validate(user);
        log.info("Creating user {}", user);
        return super.create(user);
    }

    @SneakyThrows
    @PutMapping
    public User upDate(@Valid @RequestBody User user) {
        validate(user);
        log.info("Updating user {}", user);
        return super.upDate(user);
    }

    @Override
    public void validate(User data) {
        if (data.getName() == null || data.getName().isBlank()) {
            log.warn("Поле\"Имя\" отсутствует.");
            data.setName(data.getLogin());
        }
    }
}
