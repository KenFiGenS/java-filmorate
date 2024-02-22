package ru.yandex.practicum.filmorate.service;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.storage.FilmDbStorage;

import java.util.List;

@Service
public class FilmService extends BaseService<Film> {
    private final FilmDbStorage filmDbStorage;

    public FilmService(FilmDbStorage filmDbStorage) {
        super(filmDbStorage);
        this.filmDbStorage = filmDbStorage;
    }
    @Override
    public Film create(Film data) {
        return super.create(data);
    }

    @Override
    public Film upDate(Film data) {
        return super.upDate(data);
    }

    @Override
    public Film getById(int id) {
        return super.getById(id);
    }

    @Override
    public List<Film> getAll() {
        return super.getAll();
    }

    public void addLike(int id, int userId) {
        filmDbStorage.addLike(id, userId);
    }

    public void removeLike(int id, int userId) {
        filmDbStorage.removeLike(id, userId);
    }

    public List<Film> topFilms(int count) {

        return filmDbStorage.getMostPopularFilm(count);
    }

    public Mpa getMpaById(int id) {
        return filmDbStorage.getMpaById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    public List<Mpa> getAllMpa() {
        return filmDbStorage.getAllMpa();
    }

    public List<Genre> getAllGenres() {
        return filmDbStorage.getAllGenres();
    }

    public Genre getGenresById(int id) {
        return filmDbStorage.getGenreById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }
}
