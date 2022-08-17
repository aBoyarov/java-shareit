package ru.practicum.shareit.booking.dto;

import lombok.Data;

import javax.validation.constraints.FutureOrPresent;
import java.time.LocalDateTime;

/**
 * @author Andrey Boyarov
 */
@Data
public class BookingConsumerDto {
    private Long id;
    private Long itemId;
    @FutureOrPresent
    private LocalDateTime start;
    @FutureOrPresent
    private LocalDateTime end;
}
