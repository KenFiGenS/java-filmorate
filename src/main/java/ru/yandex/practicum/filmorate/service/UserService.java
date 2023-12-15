package ru.yandex.practicum.filmorate.service;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
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

    public void addFriend(int id, int friendId) {
        if (id <= 0 || friendId <= 0) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Параметры ID заданы не верно");
        }
        User user1 = baseStorage.getById(id);
        User user2 = baseStorage.getById(friendId);
        List<Integer> friends1 = user1.getFriends();
        List<Integer> friends2 = user2.getFriends();
        if (friends1.contains(friendId) || friends2.contains(id)) {
            throw new IllegalArgumentException("Данный пользователь уже в списке друзей");
        } else {
            friends1.add(friendId);
            friends2.add(id);
        }
    }

    public void removeFriend(int id, int friendId) {
        List<Integer> friends1 = baseStorage.getById(id).getFriends();
        List<Integer> friends2 = baseStorage.getById(friendId).getFriends();
        if (friends1.contains(friendId) && friends2.contains(id)) {
            friends1.remove(friends1.indexOf(friendId));
            friends2.remove(friends2.indexOf(id));
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Данный пользователь списке друзей отсутствует");
        }
    }

    public List<User> getAllFriends(int id) {
        List<User> allFriends = baseStorage.getById(id).getFriends().stream()
                .map(id1 -> baseStorage.getById(id1)).collect(Collectors.toList());
        return allFriends;
    }

    public List<User> generalFriendsList(int id, int otherId) {
        List<Integer> friends1 = baseStorage.getById(id).getFriends();
        List<Integer> friends2 = baseStorage.getById(otherId).getFriends();
        List<User> generalList = friends1.stream()
                .filter(id1 -> friends2.contains(id1))
                .findFirst()
                .stream()
                .map(integer -> baseStorage.getById(integer))
                .collect(Collectors.toList());

        return generalList;
    }


}
