package ru.practicum.shareit.item.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;

/**
 * @author Andrey Boyarov
 */
@Data
@AllArgsConstructor
public class CommentDto {
    private Long id;
    @NotEmpty
    private String text;
    private String authorName;
    LocalDateTime created;

    public CommentDto() {
    }
}
