package ru.yandex.practicum.filmorate.service;

import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.FilmDbStorage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Comparator.comparing;

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
//
//    @Override
//    public List<Film> getAll() {
//        return super.getAll();
//    }

    @Override
    public Film getById(int id) {
        return super.getById(id);
    }

//    public Film addRate(int id, int userId) {
//        Film currentFilm = getById(id);
//        Set<Integer> userSet = currentFilm.getUserList();
//        if (!userSet.add(userId)) {
//            throw new IllegalArgumentException("Данный пользователь уже оценивал этот фильм");
//        }
//        return currentFilm;
//    }
//
//    public Film removeRate(int id, int userId) {
//        Film currentFilm = getById(id);
//        Set<Integer> userSet = currentFilm.getUserList();
//        if (!userSet.remove(userId)) {
//            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Параметры ID заданы не верно");
//        }
//        return currentFilm;
//    }

    public List<Film> topFilms(int count) {
        return filmDbStorage.getMostPopularFilm(count).orElse(filmDbStorage.getAll());
    }
}
