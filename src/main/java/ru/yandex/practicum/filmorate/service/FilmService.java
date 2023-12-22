package ru.yandex.practicum.filmorate.service;

import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;

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

    public Film addRate(int id, User user) {
        Film currentFilm = getById(id);
        Set<User> userSet = currentFilm.getUserList();
        if (!userSet.add(user)) {
            throw new IllegalArgumentException("Данный пользователь уже оценивал этот фильм");
        }
        return currentFilm;
    }

    public Film removeRate(int id, User user) {
        Film currentFilm = getById(id);
        Set<User> userSet = currentFilm.getUserList();
        if (!userSet.remove(user)) {
            throw new IllegalArgumentException("Данный пользователь еще не оценивал этот фильм");
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
