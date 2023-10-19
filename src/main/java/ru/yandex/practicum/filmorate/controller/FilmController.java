package ru.yandex.practicum.filmorate.controller;


import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.controllerException.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@Slf4j
public class FilmController {
    private final Map<Integer, Film> films = new HashMap<>();
    private int countId = 1;

    @SneakyThrows
    @GetMapping("/films")
    public List<Film> getAllFilms() {
        if (films.size() == 0) {
            log.warn("Список фильмов пуст");
            throw new ValidationException("Список фильмов пуст");
        }
        return films.values().stream().collect(Collectors.toList());
    }

    @SneakyThrows
    @PostMapping("/films")
    public Film newFilm(@RequestBody Film film) {
        if (films.containsKey(film.getId())) {
            log.warn("Данный фильм уже создан");
            throw new ValidationException("Данный фильм уже создан");
        } else if (film.getName().isBlank()) {
            log.warn("Отсутствует название фильма");
            throw new ValidationException("Отсутствует название фильма");
        } else if (film.getDescription().length() > 200) {
            log.warn("Максимальная длина описания — 200 символов");
            throw new ValidationException("Максимальная длина описания — 200 символов");
        } else if (film.getReleaseDate().isBefore(LocalDate.of(1895, 12, 28))) {
            log.warn("Дата релиза — не раньше 28.12.1895");
            throw new ValidationException("Дата релиза — не раньше 28.12.1895");
        } else if (film.getDuration() <= 0) {
            log.warn("Продолжительность фильма должна быть положительной");
            throw new ValidationException("Продолжительность фильма должна быть положительной");
        }
        Film newFilm = Film.builder()
                .id(countId++)
                .name(film.getName())
                .description(film.getDescription())
                .releaseDate(film.getReleaseDate())
                .duration(film.getDuration())
                .build();
        films.put(newFilm.getId(), newFilm);
        log.info("Пользователь успешно добавлен");
        return newFilm;
    }

    @SneakyThrows
    @PutMapping("/films")
    public Film upDateFilm(@RequestBody Film film) {
        if (!films.containsKey(film.getId())) {
            log.warn("Данный фильм отсутствует");
            throw new ValidationException("Данный фильм отсутствует");
        } else if (film.getName().isBlank()) {
            log.warn("Отсутствует название фильма");
            throw new ValidationException("Отсутствует название фильма");
        } else if (film.getDescription().length() > 200) {
            log.warn("Максимальная длина описания — 200 символов");
            throw new ValidationException("Максимальная длина описания — 200 символов");
        } else if (film.getReleaseDate().isBefore(LocalDate.of(1895, 12, 28))) {
            log.warn("Дата релиза — не раньше 28.12.1895");
            throw new ValidationException("Дата релиза — не раньше 28.12.1895");
        } else if (film.getDuration() <= 0) {
            log.warn("Продолжительность фильма должна быть положительной");
            throw new ValidationException("Продолжительность фильма должна быть положительной");
        }
        Film newFilm = Film.builder()
                .id(film.getId())
                .name(film.getName())
                .description(film.getDescription())
                .releaseDate(film.getReleaseDate())
                .duration(film.getDuration())
                .build();
        films.put(newFilm.getId(), newFilm);
        log.info("Данные пользователя успешно обновлены");
        return newFilm;
    }
}
