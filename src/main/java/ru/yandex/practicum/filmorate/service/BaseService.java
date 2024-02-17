package ru.yandex.practicum.filmorate.service;

import lombok.SneakyThrows;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import ru.yandex.practicum.filmorate.model.BaseUnit;
import ru.yandex.practicum.filmorate.storage.BaseStorage;

import java.util.List;


public abstract class BaseService<T extends BaseUnit> {

    private final BaseStorage<T> baseStorage;

    public BaseService(BaseStorage<T> baseStorage) {
        this.baseStorage = baseStorage;
    }

    public BaseStorage<T> getBaseStorage() {
        return baseStorage;
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
        return baseStorage.getById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "This user has not been created yet."));
    }

}
