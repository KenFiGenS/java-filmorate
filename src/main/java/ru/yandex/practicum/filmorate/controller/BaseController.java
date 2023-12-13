package ru.yandex.practicum.filmorate.controller;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import ru.yandex.practicum.filmorate.model.BaseUnit;
import ru.yandex.practicum.filmorate.storage.BaseStorage;

import java.util.List;

/**
 * @noinspection checkstyle:Regexp
 */


public abstract class BaseController<T extends BaseUnit> {
    @Autowired
    BaseStorage<T> baseStorage;

    @SneakyThrows
    public T create(T data) {

        return baseStorage.create(data);
    }

    @SneakyThrows
    public T upDate(T data) {

        return baseStorage.upDate(data);
    }

    @SneakyThrows
    public List<T> getAll() {

        return baseStorage.getAll();
    }

    public abstract void validate(T data);
}
