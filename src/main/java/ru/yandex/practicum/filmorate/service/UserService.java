package ru.yandex.practicum.filmorate.service;

import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.UserDbStorage;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService extends BaseService<User> {
    private final UserDbStorage userDbStorage;

    public UserService(UserDbStorage userDbStorage) {
        super(userDbStorage);
        this.userDbStorage = userDbStorage;
    }

    @Override
    public User create(User data) {
        return super.create(data);
    }

    @Override
    public User upDate(User data) {
        return super.upDate(data);
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
        if (getById(id) != null && getById(friendId) != null) {
            userDbStorage.addFriend(id, friendId);
        }
    }

    public void removeFriend(int id, int friendId) {
        userDbStorage.removeFriend(id, friendId);
    }

    public List<User> getAllFriends(int id) {
        return userDbStorage.getAllFriends(id).orElse(new ArrayList<>());
    }

    public List<User> getGeneralFriendsList(int id, int otherId) {
        List<User> generalFriendsList;
        generalFriendsList = userDbStorage.getGeneralFriends(id, otherId).get();
        return generalFriendsList;
    }
}
