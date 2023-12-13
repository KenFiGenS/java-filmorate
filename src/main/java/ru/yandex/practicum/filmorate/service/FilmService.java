package ru.yandex.practicum.filmorate.service;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;

import java.util.List;
import java.util.Set;

@Component
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

    public int addRate(User user, Film film) {
        Set<User> userSet = film.getUserList();
        int currentRate = film.getRate();
        if (userSet.contains(user)) {
            throw new IllegalArgumentException("Данный пользователь уже оценивал этот фильм");
        } else {
            userSet.add(user);
            film.setRate(++currentRate);
        }
        return currentRate;
    }
}
