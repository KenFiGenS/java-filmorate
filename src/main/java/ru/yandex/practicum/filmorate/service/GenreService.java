package ru.yandex.practicum.filmorate.service;

import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.storage.GenreStorage;

@Service
public class GenreService extends BaseService<Genre> {

    public GenreService(GenreStorage genreStorage) {
        super(genreStorage);
    }

}
