package ru.yandex.practicum.filmorate.service;

import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.FilmStorage;

import java.util.List;

@Service
public class FilmService extends BaseService<Film> {
    private final FilmStorage filmStorage;

    public FilmService(FilmStorage filmStorage) {
        super(filmStorage);
        this.filmStorage = filmStorage;
    }

    @Override
    public Film getById(int id) {
        return super.getById(id);
    }

    @Override
    public List<Film> getAll() {
        return super.getAll();
    }

    public Film create(Film data) {
        return filmStorage.create(data);
    }

    public Film upDate(Film data) {
        return filmStorage.upDate(data);
    }


    public void addLike(int id, int userId) {
        filmStorage.addLike(id, userId);
    }

    public void removeLike(int id, int userId) {
        filmStorage.removeLike(id, userId);
    }

    public List<Film> topFilms(int count) {

        return filmStorage.getMostPopularFilm(count);
    }
}
