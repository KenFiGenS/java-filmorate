package ru.yandex.practicum.filmorate.service;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
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

    public List<User> addFriend(User user1, User user2) {
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

    public List<User> deleteFriend(User user1, User user2) {
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

    public List<User> generalFriendsList(User user1, User user2) {
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
