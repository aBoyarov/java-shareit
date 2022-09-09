package shareit.item.dto;

import lombok.Data;
import shareit.booking.dto.NestedBookingDto;

import java.util.List;

/**
 * @author Andrey Boyarov
 */
@Data
public class ItemOwnerDto {
    private Long id;

    private String name;

    private String description;

    private Boolean available;

    private List<CommentDto> comments;

    private NestedBookingDto lastBooking;

    private NestedBookingDto nextBooking;
}
