package ru.yandex.practicum.filmorate.storage;

import lombok.SneakyThrows;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import ru.yandex.practicum.filmorate.controllerException.ValidationException;
import ru.yandex.practicum.filmorate.model.BaseUnit;

import java.util.*;

/**
 * @noinspection checkstyle:Regexp
 */

public abstract class BaseStorage<T extends BaseUnit> {

    private final Map<Integer, T> storage = new HashMap<>();
    private int generatedId;

    public Map<Integer, T> getStorage() {
        return storage;
    }

    private int getGeneratedId() {
        return generatedId;
    }

    @SneakyThrows
    public T create(T data) {
        for (T data1 : storage.values()) {
            if (data1.equals(data)) {
                throw new ValidationException(String.format("The data %s has already been creating", data));
            }
        }

        data.setId(++generatedId);
        storage.put(data.getId(), data);
        return data;
    }

    @SneakyThrows
    public T upDate(T data) {
        if (!storage.containsKey(data.getId())) {
            throw new ValidationException(String.format("Data %s not found", data));
        }
        storage.put(data.getId(), data);
        return data;
    }

    @SneakyThrows
    public List<T> getAll() {

        return new ArrayList<>(storage.values());
    }

    public Optional<T> getById(int id) {
        if (storage.containsKey(id)) {
            return Optional.of(storage.get(id));
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "This user has not been created yet.");
        }
    }
}
