package ru.yandex.practicum.filmorate.service;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.storage.BaseStorage;
import ru.yandex.practicum.filmorate.storage.GenreDbStorage;

import java.util.List;

@Service
public class GenreService extends BaseService<Genre> {
    GenreDbStorage genreDbStorage;
    public GenreService(GenreDbStorage genreDbStorage) {
        super(genreDbStorage);
        this.genreDbStorage = genreDbStorage;
    }
    public List<Genre> getAllGenres() {
        return genreDbStorage.getAll();
    }

    public Genre getGenresById(int id) {
        return genreDbStorage.getById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }
}
