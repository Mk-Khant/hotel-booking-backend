package org.example.hotelmanagementbackend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class RoomNotFoundException extends ResponseStatusException {

    public RoomNotFoundException(String roomName) {
        super(HttpStatus.BAD_REQUEST, "Room does not exist: " + roomName);
    }
}
