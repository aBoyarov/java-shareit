package ru.practicum.shareit.item.repository;

import ru.practicum.shareit.item.model.Item;

import java.util.List;

/**
 * @author Andrey Boyarov
 */
public interface ItemStorage {
    Item addNewItem(Item item);
    void updateItem(Item item);
    Item getId(long itemId);
    List<Item> getAllItemsByOwnerId(long id);
    List<Item> search(String text);
}
