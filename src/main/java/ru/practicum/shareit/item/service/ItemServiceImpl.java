package ru.practicum.shareit.item.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.exception.ItemAvailableException;
import ru.practicum.shareit.exception.UserNotFoundException;
import ru.practicum.shareit.item.ItemMapper;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.repository.ItemStorage;
import ru.practicum.shareit.user.repository.UserStorage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Andrey Boyarov
 */
@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService{
    private final UserStorage userStorage;
    private final ItemStorage itemStorage;
    private final ItemMapper itemMapper;

    @Override
    public ItemDto addNewItem(ItemDto itemDto, long userId) throws UserNotFoundException, ItemAvailableException {
        validUserId(userId);
        validAvailable(itemDto);
        Item item = itemStorage.addNewItem(itemMapper.toItem(itemDto, userId));
        return itemMapper.toItemDto(item);
    }

    private void validAvailable(ItemDto itemDto) throws ItemAvailableException {
        if(itemDto.getAvailable() == null){
            throw new ItemAvailableException("available can't be null");
        }
    }

    private void validUserId(long userId) throws UserNotFoundException {
        if(!userStorage.getAllUsers().containsKey(userId)){
            throw new UserNotFoundException("User not found");
        }
    }

    @Override
    public ItemDto updateItem(ItemDto itemDto, long itemId, long userId) throws UserNotFoundException, ItemAvailableException {
        validUserId(userId);
        if(!itemStorage.getId(itemId).getOwner().equals(userId)){
            throw new UserNotFoundException("user is not the owner");
        }
        itemDto.setId(itemId);
        Item item = itemMapper.toItem(itemDto, userId);
        itemStorage.updateItem(item);
        return itemMapper.toItemDto(item);
    }

    @Override
    public ItemDto getId(long itemId) {
        return itemMapper.toItemDto(itemStorage.getId(itemId));
    }

    @Override
    public List<ItemDto> getAllItemsByOwnerId(long id) {
        return itemStorage.getAllItemsByOwnerId(id).stream()
                .map(itemMapper::toItemDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<ItemDto> search(String text) {
        if(text.isEmpty()){
            return new ArrayList<>();
        }
        return itemStorage.search(text).stream()
                .map(itemMapper::toItemDto)
                .collect(Collectors.toList());
    }
}
