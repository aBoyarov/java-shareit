package ru.practicum.shareit.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.model.User;

import java.util.List;

/**
 * @author Andrey Boyarov
 */
public interface UserRepository extends JpaRepository<User, Long> {
}
