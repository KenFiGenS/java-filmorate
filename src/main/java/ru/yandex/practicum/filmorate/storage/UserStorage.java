package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.User;

import java.util.List;

public interface UserStorage extends Storage<User> {

    public User create(User data);

    public User upDate(User data);

    public void addFriend(int id, int friendId);

    public List<User> getGeneralFriends(int id, int otherId);

    public List<User> getAllFriends(int id);

    public void removeFriend(int id, int friendId);
}
