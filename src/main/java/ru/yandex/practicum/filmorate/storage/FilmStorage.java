package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.List;

public interface FilmStorage extends Storage<Film> {

    public Film create(Film data);

    public Film upDate(Film data);

    public List<Film> getMostPopularFilm(int count);

    public void addLike(int id, int userId);

    public void removeLike(int id, int userId);
}
