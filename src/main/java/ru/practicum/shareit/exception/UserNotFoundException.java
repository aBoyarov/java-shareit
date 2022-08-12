package ru.practicum.shareit.exception;

/**
 * @author Andrey Boyarov
 */
public class UserNotFoundException extends Exception{
    public UserNotFoundException(String message) {
        super(message);
    }
}
