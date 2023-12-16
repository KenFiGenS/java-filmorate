package ru.yandex.practicum.filmorate.controller;


import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.controllerException.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/films")
@Slf4j
public class FilmController extends BaseController<Film> {

    private static final LocalDate START_RELEASE_DATE = LocalDate.of(1895, 12, 28);

    @PostMapping
    public Film create(@Valid @RequestBody Film film) {
        validate(film);
        log.info("Creating film {}", film);
        return super.create(film);
    }

    @PutMapping
    public Film upDate(@Valid @RequestBody Film film) {
        validate(film);
        log.info("Updating film {}", film);
        return super.upDate(film);
    }

    @SneakyThrows
    @PutMapping("{id}/like/{userId}")
    public Film addRate(@PathVariable int id, @PathVariable int userId) {
        User currentUser = userService.getById(userId);
        log.info("Rating increase");
        return filmService.addRate(id, currentUser);
    }

    @SneakyThrows
    @DeleteMapping("{id}/like/{userId}")
    public Film removeRate(@PathVariable int id, @PathVariable int userId) {
        User currentUser = userService.getById(userId);
        log.info("Rating decrease");
        return filmService.removeRate(id, currentUser);
    }

    @SneakyThrows
    @GetMapping
    public List<Film> getAll() {
        log.info("Getting all film");
        return super.getAll();
    }

    @SneakyThrows
    @GetMapping("{id}")
    public Film getById(@PathVariable int id) {
        log.info("Getting film by ID");
        return super.getById(id);
    }

    @SneakyThrows
    @GetMapping("popular")
    public List<Film> getTopFilms(@RequestParam(defaultValue = "10") int count) {
        log.info("Getting the first popular film");
        return filmService.topFilms(count);
    }

    @SneakyThrows
    @Override
    public void validate(Film data) {
        if (data.getReleaseDate().isBefore(START_RELEASE_DATE)) {
            log.warn("Дата релиза — не раньше 28.12.1895");
            throw new ValidationException("Дата релиза — не раньше 28.12.1895");
        }
    }
}
