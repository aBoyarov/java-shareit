package ru.practicum.shareit.exception;

import java.util.function.Supplier;

/**
 * @author Andrey Boyarov
 */
public class UserNotFoundException extends Exception {
    public UserNotFoundException(String message) {
        super(message);
    }

}
