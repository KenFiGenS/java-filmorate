package ru.yandex.practicum.filmorate.storageTest;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.storage.FilmDbStorage;

import java.sql.Date;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@JdbcTest // указываем, о необходимости подготовить бины для работы с БД
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class FilmDbStorageTest {
    private final JdbcTemplate jdbcTemplate;
    private FilmDbStorage filmDbStorage;

    @BeforeEach
    void setUp() {
        filmDbStorage = new FilmDbStorage(jdbcTemplate);
    }

    @Test
    public void testFindFilmById() {
        // Подготавливаем данные для теста
        Film newFilm = new Film(1, "11111", "1111111", Date.valueOf("2001-11-11"),
                100, new Mpa(1, "G"));
        filmDbStorage.create(newFilm);

        // вызываем тестируемый метод
        Film savedFilm = filmDbStorage.getById(1);

        // проверяем утверждения
        assertThat(savedFilm)
                .isNotNull() // проверяем, что объект не равен null
                .usingRecursiveComparison() // проверяем, что значения полей нового
                .isEqualTo(savedFilm);        // и сохраненного пользователя - совпадают
    }

    @Test
    public void testFindAllFilm() {
        Film newFilm = new Film(1, "11111", "1111111", Date.valueOf("2001-11-11"),
                100, new Mpa(1, "G"));
        Film newFilm2 = new Film(2, "22222", "2222222", Date.valueOf("2001-11-12"),
                120, new Mpa(1, "G"));
        filmDbStorage.create(newFilm);
        filmDbStorage.create(newFilm2);
        List<Film> filmList = List.of(newFilm2, newFilm);

        List<Film> savedFilms = filmDbStorage.getAll();

        assertThat(savedFilms.stream().allMatch(f -> filmList.contains(f)));        // и сохраненного пользователя - совпадают
    }

    @Test
    public void testUpdateFilm() {
        Film newFilm = new Film(1, "11111", "1111111", Date.valueOf("2001-11-11"),
                100, new Mpa(1, "G"));
        filmDbStorage.create(newFilm);
        Film upDateFilm = new Film(1, "33333", "3333333", Date.valueOf("2001-11-11"),
                100, new Mpa(1, "G"));
        filmDbStorage.upDate(upDateFilm);

        Film savedFilm = filmDbStorage.getById(1);

        // проверяем утверждения
        assertThat(savedFilm)
                .isNotNull()
                .usingRecursiveComparison()
                .isEqualTo(upDateFilm);
    }
}
