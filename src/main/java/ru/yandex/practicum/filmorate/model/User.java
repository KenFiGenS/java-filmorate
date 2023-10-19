package ru.yandex.practicum.filmorate.model;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class User {
    final int id;
    final String email;
    final String login;
    final String name;
    final LocalDate birthday;
}
