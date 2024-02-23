package ru.yandex.practicum.filmorate.storageTest;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.storage.GenreDbStorage;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@JdbcTest
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class GenreDbStorageTest {
    private final JdbcTemplate jdbcTemplate;
    private GenreDbStorage genreDbStorage;

    @BeforeEach
    void setUp() {
        genreDbStorage = new GenreDbStorage(jdbcTemplate);
    }

    @Test
    public void testFindGenreById() {
        Genre newGenre = new Genre(1, "Комедия");
        Genre genre = genreDbStorage.getById(1);

        assertThat(genre)
                .isNotNull()
                .usingRecursiveComparison()
                .isEqualTo(newGenre);
    }

    @Test
    public void testFindAllGenre() {
        List<Genre> allGenres = List.of(new Genre(1, "Комедия"),
                new Genre(2, "Драма"),
                new Genre(3, "Мультфильм"),
                new Genre(4, "Триллер"),
                new Genre(5, "Докуметнтальный"),
                new Genre(6, "Боевик"));

        List<Genre> savedGenres = genreDbStorage.getAll();

        assertThat(savedGenres.stream().allMatch(genre -> allGenres.contains(genre)));
    }
}
