package ru.practicum.shareit.item;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.repository.ItemRepository;

/**
 * @author Andrey Boyarov
 */
@Component
@RequiredArgsConstructor
public class ItemMapper {
    private final ItemRepository itemRepository;
    public ItemDto toItemDto(Item item) {
        return ItemDto.builder()
                .id(item.getId())
                .name(item.getName())
                .description(item.getDescription())
                .available(item.getAvailable())
                .build();
    }

    public Item toItem(ItemDto itemDto, long userId) {
        return Item.builder()
                .id(itemDto.getId())
                .name(itemDto.getName() != null ? itemDto.getName() : itemRepository.findById(itemDto.getId()).get().getName())
                .description(itemDto.getDescription() != null ?
                        itemDto.getDescription() : itemRepository.findById(itemDto.getId()).get().getDescription())
                .available(itemDto.getAvailable() != null ?
                        itemDto.getAvailable() : itemRepository.findById(itemDto.getId()).get().getAvailable())
                .owner(userId)
                .request(null)
                .build();
    }
}
