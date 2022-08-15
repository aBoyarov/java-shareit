package ru.practicum.shareit.item.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.shareit.exception.UserNotFoundException;
import ru.practicum.shareit.item.model.Item;

import java.util.List;

/**
 * @author Andrey Boyarov
 */
public interface ItemRepository extends JpaRepository<Item, Long> {
    @Query(" select i from Item i " +
            "where (upper(i.name) like upper(concat('%', ?1, '%')) " +
            " or upper(i.description) like upper(concat('%', ?1, '%')))" +
            " and i.available = true")
    List<Item> search(String text);

    List<Item> findAllByOwnerIdOrderByIdAsc(Long userId);

    Item findByIdOrderByIdDesc(long itemId) throws UserNotFoundException;

}