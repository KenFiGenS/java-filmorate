package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.BaseUnit;

import java.util.List;

public interface Storage <T extends BaseUnit> {

    public  List<T> getAll();

    public T getById(int id);
}
