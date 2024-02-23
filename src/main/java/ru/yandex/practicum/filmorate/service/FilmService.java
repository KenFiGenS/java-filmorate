package ru.yandex.practicum.filmorate.service;

import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.BaseStorage;

import java.util.List;

@Service
public class FilmService extends BaseService<Film> {
    private final BaseStorage<Film> filmDbStorage;

    public FilmService(BaseStorage<Film> filmDbStorage) {
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
}
