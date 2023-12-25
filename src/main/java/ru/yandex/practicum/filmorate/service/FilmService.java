package ru.yandex.practicum.filmorate.service;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class FilmService extends BaseService<Film> {
    @Override
    public Film create(Film data) {
        return super.create(data);
    }

    @Override
    public Film upDate(Film data) {
        return super.upDate(data);
    }

    @Override
    public List<Film> getAll() {
        return super.getAll();
    }

    @Override
    public Film getById(int id) {
        return super.getById(id);
    }

    public Film addRate(int id, int userId) {
        Film currentFilm = getById(id);
        Set<Integer> userSet = currentFilm.getUserList();
        if (!userSet.add(userId)) {
            throw new IllegalArgumentException("Данный пользователь уже оценивал этот фильм");
        }
        return currentFilm;
    }

    public Film removeRate(int id, int userId) {
        Film currentFilm = getById(id);
        Set<Integer> userSet = currentFilm.getUserList();
        if (!userSet.remove(userId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Параметры ID заданы не верно");
        }
        return currentFilm;
    }

    public List<Film> topFilms(int count) {
        List<Film> result = getBaseStorage().getAll().stream()
                .sorted((f0, f1) -> f0.getRate() > f1.getRate() ? -1 : 1)
                .limit(count)
                .collect(Collectors.toList());
        return result;
    }
}
