package ru.yandex.practicum.filmorate.controller;


import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.controllerException.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.service.FilmService;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.sql.Date;
import java.util.List;

@RestController
@RequestMapping
@Slf4j
public class FilmController extends BaseController<Film> {

    private final FilmService filmService;

    public FilmController(FilmService filmService) {
        super(filmService);
        this.filmService = filmService;
    }

    private static final Date START_RELEASE_DATE = Date.valueOf("1895-12-28");

    @PostMapping("/films")
    public Film create(@Valid @RequestBody Film film) {
        validate(film);
        log.info("Creating film {}", film);
        return super.create(film);
    }

    @PutMapping("/films")
    public Film upDate(@Valid @RequestBody Film film) {
        validate(film);
        log.info("Updating film {}", film);
        return super.upDate(film);
    }

    @SneakyThrows
    @GetMapping("/films/{id}")
    public Film getById(@PathVariable int id) {
        log.info("Getting film by ID");
        return super.getById(id);
    }

    @SneakyThrows
    @GetMapping("/films")
    public List<Film> getAll() {
        log.info("Getting all film");
        return super.getAll();
    }

    @SneakyThrows
    @PutMapping("/films/{id}/like/{userId}")
    public void addLike(@PathVariable int id, @PathVariable int userId) {
        log.info("Rating increase");
        filmService.addLike(id, userId);
    }

    @SneakyThrows
    @DeleteMapping("/films/{id}/like/{userId}")
    public void removeLike(@PathVariable int id, @PathVariable int userId) {
        log.info("Rating decrease");
        filmService.removeLike(id, userId);
    }

    @SneakyThrows
    @GetMapping("/films/popular")
    public List<Film> getTopFilms(@RequestParam(defaultValue = "10") @Positive int count) {
        log.info("Getting the first popular film");
        return filmService.topFilms(count);
    }

    @SneakyThrows
    @GetMapping("/mpa/{id}")
    public Mpa getMpaById(@PathVariable int id) {
        log.info("Getting MPA by ID");
        return filmService.getMpaById(id);
    }

    @SneakyThrows
    @GetMapping("/genres")
    public List<Genre> getAllGenres() {
        log.info("Getting all genres");
        return filmService.getAllGenres();
    }

    @SneakyThrows
    @GetMapping("/genres/{id}")
    public Genre getGenresById(@PathVariable int id) {
        log.info("Getting MPA by ID");
        return filmService.getGenresById(id);
    }

    @SneakyThrows
    @GetMapping("/mpa")
    public List<Mpa> getAllMpa() {
        log.info("Getting all MPA");
        return filmService.getAllMpa();
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
