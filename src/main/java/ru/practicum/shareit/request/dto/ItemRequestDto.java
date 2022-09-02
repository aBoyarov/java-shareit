package ru.practicum.shareit.request.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.user.model.User;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author Andrey Boyarov
 */
@Data
@AllArgsConstructor
public class ItemRequestDto {

    private Long id;
    @NotNull
    private String description;
    private final LocalDateTime created = LocalDateTime.now();
    private User requestor;
    private List<ItemDto> items;


    public ItemRequestDto() {
    }
}
