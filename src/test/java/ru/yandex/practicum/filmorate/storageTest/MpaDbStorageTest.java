package ru.yandex.practicum.filmorate.storageTest;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.storage.MpaDbStorage;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@JdbcTest
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class MpaDbStorageTest {
    private final JdbcTemplate jdbcTemplate;
    private MpaDbStorage mpaDbStorage;

    @BeforeEach
    void setUp() {
        mpaDbStorage = new MpaDbStorage(jdbcTemplate);
    }

    @Test
    public void testFindMpaById() {
        Mpa newMpa = new Mpa(1, "G");
        Mpa mpa = mpaDbStorage.getById(1);

        assertThat(mpa)
                .isNotNull()
                .usingRecursiveComparison()
                .isEqualTo(newMpa);
    }

    @Test
    public void testFindAllMpa() {
        List<Mpa> allMpa = List.of(new Mpa(1, "G"),
                new Mpa(2, "PG"),
                new Mpa(3, "PG-13"),
                new Mpa(4, "R"),
                new Mpa(5, "NC-17"));

        List<Mpa> savedMpa = mpaDbStorage.getAll();

        assertThat(savedMpa.stream().allMatch(genre -> allMpa.contains(genre)));
    }
}
