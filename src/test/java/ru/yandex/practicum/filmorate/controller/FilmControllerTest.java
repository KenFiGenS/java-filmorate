package ru.yandex.practicum.filmorate.controller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import ru.yandex.practicum.filmorate.controllerException.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;
import ru.yandex.practicum.filmorate.storage.FilmDbStorage;

import java.time.LocalDate;

class FilmControllerTest {

    private FilmController filmController;

    @BeforeEach
    void setUp() {
        filmController = new FilmController(new FilmService(new FilmDbStorage(new JdbcTemplate())));
    }

//    @Test
//    void validate() {
//        Film newFilm = Film.builder()
//                .name("Hobbit")
//                .description("Very interesting film")
//                .releaseDate(LocalDate.of(2015,12, 10))
//                .duration(120)
//                .build();
//        filmController.validate(newFilm);
//    }
//
//    @Test
//    void validateNegative() {
//        Film newFilm = Film.builder()
//                .name("Hobbit")
//                .description("Very interesting film")
//                .releaseDate(LocalDate.of(1015,12, 10))
//                .duration(120)
//                .build();
//        Assertions.assertThrows(ValidationException.class, () -> filmController.validate(newFilm));
//    }
}
