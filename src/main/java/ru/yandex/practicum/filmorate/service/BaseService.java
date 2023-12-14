package ru.yandex.practicum.filmorate.service;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.BaseUnit;
import ru.yandex.practicum.filmorate.storage.BaseStorage;


import java.util.List;

@Service
public abstract class BaseService<T extends BaseUnit> {
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

    @SneakyThrows
    public T getById(int id) {
        return baseStorage.getById(id);
    }
}
