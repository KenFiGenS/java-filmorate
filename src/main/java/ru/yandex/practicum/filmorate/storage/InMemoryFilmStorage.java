package ru.yandex.practicum.filmorate.storage;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Film;

/** @noinspection checkstyle:Regexp*/
@Component
public class InMemoryFilmStorage extends BaseStorage<Film> {

}