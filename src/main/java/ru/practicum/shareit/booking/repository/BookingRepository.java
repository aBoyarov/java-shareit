package ru.practicum.shareit.booking.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.shareit.booking.Status;
import ru.practicum.shareit.booking.model.Booking;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author Andrey Boyarov
 */
public interface BookingRepository extends JpaRepository<Booking, Long> {

    List<Booking> findAllByBookerIdAndStartBeforeAndEndAfterOrderByStartDesc(Long userId,
                                                                             LocalDateTime beforeTime,
                                                                             LocalDateTime afterTime);
    Integer countByItemIdAndBookerIdAndStatusAndStartBefore(long itemId,
                                                            long userId,
                                                            Status waiting,
                                                            LocalDateTime time);
    List<Booking> findAllByItemOwnerIdAndStartBeforeAndEndAfterOrderByStartDesc(Long userId,
                                                                                LocalDateTime beforeTime,
                                                                                LocalDateTime afterTime);
    List<Booking> findAllByBookerIdOrderByStartDesc(Long userId);

    List<Booking> findAllByItemOwnerIdAndStatusOrderByStartDesc(Long userId, Status waiting);

    List<Booking> findAllByItemOwnerIdAndEndBeforeOrderByStartDesc(Long userId, LocalDateTime time);

    List<Booking> findAllByItemOwnerIdAndStartAfterOrderByStartDesc(Long userId, LocalDateTime time);

    List<Booking> findAllByBookerIdAndEndBeforeOrderByStartDesc(Long userId, LocalDateTime time);

    List<Booking> findAllByBookerIdAndStartAfterOrderByStartDesc(Long userId, LocalDateTime time);

    List<Booking> findAllByBookerIdAndStatusOrderByStartDesc(Long userId, Status waiting);

    List<Booking> findAllByItemOwnerIdOrderByStartDesc(Long userId);

    Booking getFirstByItemIdOrderByEndDesc(long itemId);

    Booking getFirstByItemIdOrderByStartAsc(long itemId);

}
