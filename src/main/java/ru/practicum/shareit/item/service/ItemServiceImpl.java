package ru.practicum.shareit.item.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.exception.ItemAvailableException;
import ru.practicum.shareit.exception.UserNotFoundException;
import ru.practicum.shareit.item.ItemMapper;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.repository.ItemRepository;
import ru.practicum.shareit.user.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Andrey Boyarov
 */
@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {
    private final UserRepository userRepository;
    private final ItemRepository itemRepository;
    private final ItemMapper itemMapper;

    @Override
    public ItemDto addNewItem(ItemDto itemDto, long userId) throws ItemAvailableException {
        userRepository.findById(userId);
        Item item = itemRepository.save(itemMapper.toItem(itemDto, userId));
        return itemMapper.toItemDto(item);
    }

    @Override
    public ItemDto updateItem(ItemDto itemDto, long itemId, long userId) throws UserNotFoundException {
        userRepository.findById(userId);
        itemDto.setId(itemId);
        Item item = itemMapper.toItem(itemDto, userId);
        itemRepository.save(item);
        return itemMapper.toItemDto(item);
    }

    @Override
    public ItemDto getId(long itemId) {
        return itemMapper.toItemDto(itemRepository.findById(itemId).get());
    }

    @Override
    public List<ItemDto> getAllItemsByOwnerId(long id) {
        return itemRepository.findAll().stream()
                .map(itemMapper::toItemDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<ItemDto> search(String text) {
        if (text.isEmpty()) {
            return new ArrayList<>();
        }
        return itemRepository.findAll().stream()
                .map(itemMapper::toItemDto)
                .collect(Collectors.toList());
    }
}
