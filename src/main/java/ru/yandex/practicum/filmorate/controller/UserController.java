package ru.yandex.practicum.filmorate.controller;


import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@Slf4j
public class UserController {
    private final Map<Integer, User> users = new HashMap<>();
    private int countId = 1;

    @SneakyThrows
    @GetMapping("/users")
    public List<User> getAllUsers() {
        if(users.size() == 0){
            log.warn("Список юзеров пуст");
            throw new ValidationException("Список юзеров пуст");
        }
        List<User> allUsers = users.values().stream().collect(Collectors.toList());
        return allUsers;
    }

    @SneakyThrows
    @PostMapping("/users")
    public User newUser(@RequestBody User user)  {
        if (users.containsKey(user.getId())){
            log.warn("Данный юзер уже создан");
            throw new ValidationException("Данный юзер уже создан");
        } else if (user.getEmail().isBlank() || !user.getEmail().contains("@")){
            log.warn("Мыльное поле пустое или задано неверно");
            throw new ValidationException("Мыльное поле пустое или задано неверно");
        } else if (user.getLogin().isBlank() || user.getLogin().contains(" ")){
            log.warn("Логин не может быть пустым и содержать пробелы");
            throw new ValidationException("Логин не может быть пустым и содержать пробелы");
        } else  if (user.getBirthday().isAfter(LocalDate.now())){
            log.warn("Дата рождения не может быть в будущем");
            throw new ValidationException("Дата рождения не может быть в будущем");
        } else if (user.getName() == null) {
            log.warn("Поле\"Имя\" отсутствует.");
            User newUser = User.builder()
                    .id(countId++)
                    .email(user.getEmail())
                    .login(user.getLogin())
                    .name(user.getLogin())
                    .birthday(user.getBirthday())
                    .build();
            users.put(newUser.getId(), newUser);
            return newUser;
        }
        User newUser = User.builder()
                .id(countId++)
                .email(user.getEmail())
                .login(user.getLogin())
                .name(user.getName())
                .birthday(user.getBirthday())
                .build();
        users.put(newUser.getId(), newUser);
        log.info("Пользователь успешно добавлен");
        return newUser;
    }

    @SneakyThrows
    @PutMapping("/users")
    public User upDateUser(@RequestBody User user) {
        if (!users.containsKey(user.getId())){
            log.warn("Данный юзер отсутствует");
            throw new ValidationException("Данный юзер отсутствует");
        } else if (user.getEmail().isBlank() || !user.getEmail().contains("@")){
            log.warn("Мыльное поле пустое или задано неверно");
            throw new ValidationException("Мыльное поле пустое или задано неверно");
        } else if (user.getLogin().isBlank() || user.getLogin().contains(" ")){
            log.warn("Логин не может быть пустым и содержать пробелы");
            throw new ValidationException("Логин не может быть пустым и содержать пробелы");
        } else  if (user.getBirthday().isAfter(LocalDate.now())){
            log.warn("Дата рождения не может быть в будущем");
            throw new ValidationException("Дата рождения не может быть в будущем");
        } else if (user.getName().isBlank() || user.getName() == null) {
            log.warn("Поле\"Имя\" отсутствует.");
            User newUser = User.builder()
                    .id(user.getId())
                    .email(user.getEmail())
                    .login(user.getLogin())
                    .name(user.getLogin())
                    .birthday(user.getBirthday())
                    .build();
            users.put(newUser.getId(), newUser);
            return newUser;
        }
        users.put(user.getId(), user);
        log.info("Данные пользователя успешно обновлены");
        return user;
    }


}
