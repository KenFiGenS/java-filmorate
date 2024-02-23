package ru.yandex.practicum.filmorate.service;

import lombok.SneakyThrows;
import ru.yandex.practicum.filmorate.model.BaseUnit;
import ru.yandex.practicum.filmorate.storage.BaseStorage;

import java.util.List;


public abstract class BaseService<T extends BaseUnit> {

    private final BaseStorage<T> baseStorage;

    public BaseService(BaseStorage<T> baseStorage) {
        this.baseStorage = baseStorage;
    }

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

    @SneakyThrows
    public T getById(int id) {
        return baseStorage.getById(id);
    }
}
