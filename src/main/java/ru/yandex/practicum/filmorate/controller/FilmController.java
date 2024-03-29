package ru.yandex.practicum.filmorate.controller;


import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.controllerException.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.sql.Date;
import java.util.List;

@RestController
@RequestMapping("/films")
@Slf4j
@Validated
public class FilmController extends BaseController<Film> {

    private final FilmService filmService;

    public FilmController(FilmService filmService) {
        super(filmService);
        this.filmService = filmService;
    }

    private static final Date START_RELEASE_DATE = Date.valueOf("1895-12-28");

    @PostMapping
    public Film create(@Valid @RequestBody Film film) {
        validate(film);
        log.info("Creating film {}", film);
        return filmService.create(film);
    }

    @PutMapping
    public Film upDate(@Valid @RequestBody Film film) {
        validate(film);
        log.info("Updating film {}", film);
        return filmService.upDate(film);
    }

    @SneakyThrows
    @GetMapping("/{id}")
    public Film getById(@PathVariable int id) {
        log.info("Getting film by ID");
        return super.getById(id);
    }

    @SneakyThrows
    @GetMapping
    public List<Film> getAll() {
        log.info("Getting all film");
        return super.getAll();
    }

    @SneakyThrows
    @PutMapping("/{id}/like/{userId}")
    public void addLike(@PathVariable int id, @PathVariable int userId) {
        log.info("Rating increase");
        filmService.addLike(id, userId);
    }

    @SneakyThrows
    @DeleteMapping("/{id}/like/{userId}")
    public void removeLike(@PathVariable int id, @PathVariable int userId) {
        log.info("Rating decrease");
        filmService.removeLike(id, userId);
    }

    @SneakyThrows
    @GetMapping("/popular")
    public List<Film> getTopFilms(@RequestParam(defaultValue = "10") @Positive int count) {
        log.info("Getting the first popular film");
        return filmService.topFilms(count);
    }


    @SneakyThrows
    @Override
    public void validate(Film data) {
        if (data.getReleaseDate().before(START_RELEASE_DATE)) {
            log.warn("Дата релиза — не раньше 28.12.1895");
            throw new ValidationException("Дата релиза — не раньше 28.12.1895");
        }
    }
}
