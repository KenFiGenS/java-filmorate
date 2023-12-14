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

    public int removeRate(User user, Film film) {
        Set<User> userSet = film.getUserList();
        int currentRate = film.getRate();
        if (userSet.contains(user)) {
            userSet.remove(user);
            film.setRate(--currentRate);
        } else {
            throw new IllegalArgumentException("Данный пользователь ещё не оценивал этот фильм");
        }
        return currentRate;
    }

    public List<Film> topFilms(int count) {
        List<Film> result = baseStorage.getAll().stream()
                .sorted((f0, f1) -> f0.getRate() > f1.getRate() ? 1 : -1)
                .limit(count)
                .collect(Collectors.toList());
        return result;
    }
}
