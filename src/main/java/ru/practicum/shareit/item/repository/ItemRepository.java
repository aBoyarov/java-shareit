package ru.practicum.shareit.item.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.shareit.item.model.Item;

/**
 * @author Andrey Boyarov
 */
public interface ItemRepository extends JpaRepository<Item, Long> {
}
