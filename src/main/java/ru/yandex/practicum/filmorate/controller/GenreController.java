package ru.yandex.practicum.filmorate.controller;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.service.GenreService;

import javax.validation.constraints.Positive;
import java.util.List;

@RestController
@RequestMapping("/genres")
@Slf4j
public class GenreController extends BaseController<Genre> {
    GenreService genreService;

    public GenreController(GenreService genreService) {
        super(genreService);
        this.genreService = genreService;
    }

    @SneakyThrows
    @GetMapping
    public List<Genre> getAllGenres() {
        log.info("Getting all genres");
        return genreService.getAllGenres();
    }

    @SneakyThrows
    @Validated
    @GetMapping("/{id}")
    public Genre getGenresById(@PathVariable @Positive int id) {
        log.info("Getting MPA by ID");
        return genreService.getGenresById(id);
    }

    @Override
    public void validate(Genre data) {

    }
}
