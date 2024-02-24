package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.BaseUnit;

public abstract class BaseStorage<T extends BaseUnit> {

//    public BaseStorage(JdbcTemplate jdbcTemplate) {
//        this.jdbcTemplate = jdbcTemplate;
//    }
//
//
//    public abstract T create(T data);
//
//
//    public abstract T upDate(T data);
//
//
//    public abstract List<T> getAll();
//
//    public abstract T getById(int id) throws Exception;
//
//    public abstract void addFriend(int id, int friendId);
//
//    public abstract void removeFriend(int id, int friendId);
//
//    public abstract List<T> getAllFriends(int id);
//
//    public abstract List<T> getGeneralFriends(int id, int otherId);
//
//    public abstract void addLike(int id, int userId);
//
//    public abstract void removeLike(int id, int userId);
//
//    public abstract List<T> getMostPopularFilm(int count);
}
