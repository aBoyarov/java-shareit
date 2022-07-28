package ru.practicum.shareit.user.repository;

import ru.practicum.shareit.user.model.User;

import java.util.Map;

/**
 * @author Andrey Boyarov
 */
public interface UserStorage {
    User create(User user);
    User getById(long id);
    Map<Long, User> getAllUsers();
    void update(User user);
    void delete(long id);
}
