package ru.yandex.practicum.filmorate.service;

import lombok.SneakyThrows;
import ru.yandex.practicum.filmorate.model.BaseUnit;
import ru.yandex.practicum.filmorate.storage.Storage;

import java.util.List;


public abstract class BaseService<T extends BaseUnit> {

    private final Storage<T> baseStorage;

    public BaseService(Storage<T> baseStorage) {
        this.baseStorage = baseStorage;
    }

    @SneakyThrows
    public List<T> getAll() {
        return baseStorage.getAll();
    }

    @SneakyThrows
    public T getById(int id) {
        return baseStorage.getById(id);
    }
}
