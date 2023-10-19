package ru.yandex.practicum.filmorate.model;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class Film {
    final int id;
    final String name;
    final String description;
    final LocalDate releaseDate;
    final int duration;
}
