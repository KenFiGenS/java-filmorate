package ru.yandex.practicum.filmorate.service;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import ru.yandex.practicum.filmorate.model.User;

import java.util.List;
import java.util.Optional;
import java.util.Set;
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
        Optional<User> user1 = getBaseStorage().getById(id);
        Optional<User> user2 = getBaseStorage().getById(friendId);
        Set<Integer> friends1 = user1.get().getFriends();
        Set<Integer> friends2 = user2.get().getFriends();
        if (friends1.contains(friendId) || friends2.contains(id)) {
            throw new IllegalArgumentException("Данный пользователь уже в списке друзей");
        } else {
            friends1.add(friendId);
            friends2.add(id);
        }
    }

    public void removeFriend(int id, int friendId) {
        Set<Integer> friends1 = getBaseStorage().getById(id).get().getFriends();
        Set<Integer> friends2 = getBaseStorage().getById(friendId).get().getFriends();
        if (friends1.contains(friendId) && friends2.contains(id)) {
            friends1.remove(friendId);
            friends2.remove(id);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Данный пользователь списке друзей отсутствует");
        }
    }

    public List<User> getAllFriends(int id) {
        List<User> allFriends = getBaseStorage().getById(id).get().getFriends().stream()
                .map(id1 -> getBaseStorage().getById(id1))
                .map(optionalUser -> optionalUser.get())
                .collect(Collectors.toList());
        return allFriends;
    }

    public List<User> generalFriendsList(int id, int otherId) {
        Set<Integer> friends1 = getBaseStorage().getById(id).get().getFriends();
        Set<Integer> friends2 = getBaseStorage().getById(otherId).get().getFriends();
        List<User> generalList = friends1.stream()
                .filter(id1 -> friends2.contains(id1))
                .findFirst()
                .stream()
                .map(integer -> getBaseStorage().getById(integer).get())
                .collect(Collectors.toList());

        return generalList;
    }


}
