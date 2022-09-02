package ru.practicum.shareit.item.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * @author Andrey Boyarov
 */
@Data
public class ItemDto {
    private Long id;
    @NotEmpty
    private String name;
    @NotEmpty
    private String description;
    private Boolean available;
    private Long requestId;

}