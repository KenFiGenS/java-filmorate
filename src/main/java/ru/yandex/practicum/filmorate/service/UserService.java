package ru.yandex.practicum.filmorate.service;

import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.User;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService extends BaseService<User> {

    @Override
    public User create(User data) {
        return super.create(data);
    }

    @Override
    public User upDate(User data) {
        return super.upDate(data);
    }

    @Override
    public List<User> getAll() {
        return super.getAll();
    }

    @Override
    public User getById(int id) {
        return super.getById(id);
    }

    public List<User> addFriend(int id, int friendId) {
        if (id <= 0 || friendId <= 0) {
            throw new IllegalArgumentException("Параметры ID заданы не верно");
        }
        User user1 = baseStorage.getById(id);
        User user2 = baseStorage.getById(friendId);
        List<User> friends1 = user1.getFriends();
        List<User> friends2 = user2.getFriends();
        if (friends1.contains(user2) || friends2.contains(user1)) {
            throw new IllegalArgumentException("Данный пользователь уже в списке друзей");
        } else {
            friends1.add(user2);
            friends2.add(user1);
        }
        return friends1;
    }

    public List<User> removeFriend(int id, int friendId) {
        if (id <= 0 || friendId <= 0) {
            throw new IllegalArgumentException("Параметры ID заданы не верно");
        }
        User user1 = baseStorage.getById(id);
        User user2 = baseStorage.getById(friendId);
        List<User> friends1 = user1.getFriends();
        List<User> friends2 = user2.getFriends();
        if (friends1.contains(user2) && friends2.contains(user1)) {
            friends1.remove(user2);
            friends2.remove(user1);
        } else {
            throw new IllegalArgumentException("Данный пользователь списке друзей отсутствует");
        }
        return friends1;
    }

    public List<User> getAllFriends(int id) {
        if (id <= 0) {
            throw new IllegalArgumentException("Параметры ID заданы не верно");
        }
        return baseStorage.getById(id).getFriends();
    }

    public List<User> generalFriendsList(int id, int otherId) {
        if (id <= 0 || otherId <= 0) {
            throw new IllegalArgumentException("Параметры ID заданы не верно");
        }
        User user1 = baseStorage.getById(id);
        User user2 = baseStorage.getById(otherId);
        List<User> friends1 = user1.getFriends();
        List<User> friends2 = user2.getFriends();
        List<User> generalList = friends1.stream()
                .filter(user -> friends2.contains(user))
                .findFirst()
                .stream().collect(Collectors.toList());

        if (generalList.isEmpty() || generalList == null) {
            throw new IllegalArgumentException("Общих друзей не найдено");
        }
        return generalList;
    }


}
