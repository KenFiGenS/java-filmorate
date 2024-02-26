package ru.yandex.practicum.filmorate.service;

import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.util.List;

@Service
public class UserService extends BaseService<User> {
    private final UserStorage userStorage;

    public UserService(UserStorage userStorage) {
        super(userStorage);
        this.userStorage = userStorage;
    }

    public User create(User data) {
        return userStorage.create(data);
    }


    public User upDate(User data) {
        return userStorage.upDate(data);
    }

    @Override
    public User getById(int id) {
        return super.getById(id);
    }

    @Override
    public List<User> getAll() {
        return super.getAll();
    }

    public void addFriend(int id, int friendId) {
        userStorage.addFriend(id, friendId);
    }

    public void removeFriend(int id, int friendId) {
        userStorage.removeFriend(id, friendId);
    }

    public List<User> getAllFriends(int id) {
        return userStorage.getAllFriends(id);
    }

    public List<User> getGeneralFriendsList(int id, int otherId) {
        return userStorage.getGeneralFriends(id, otherId);
    }
}
