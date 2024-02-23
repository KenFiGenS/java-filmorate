package ru.yandex.practicum.filmorate.service;

import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.storage.GenreDbStorage;

@Service
public class GenreService extends BaseService<Genre> {

    public GenreService(GenreDbStorage genreDbStorage) {
        super(genreDbStorage);
    }

}
