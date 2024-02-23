package ru.yandex.practicum.filmorate.storage;

import org.springframework.jdbc.core.JdbcTemplate;
import ru.yandex.practicum.filmorate.model.BaseUnit;

import java.util.List;

public abstract class BaseStorage<T extends BaseUnit> {
    protected final JdbcTemplate jdbcTemplate;

    public BaseStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    public abstract T create(T data);


    public abstract T upDate(T data);


    public abstract List<T> getAll();

    public abstract T getById(int id) throws Exception;

}
