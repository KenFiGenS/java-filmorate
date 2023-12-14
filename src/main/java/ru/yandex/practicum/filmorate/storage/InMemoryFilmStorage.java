package ru.yandex.practicum.filmorate.storage;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.List;

@Component
public class InMemoryFilmStorage extends BaseStorage<Film> {

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
}
