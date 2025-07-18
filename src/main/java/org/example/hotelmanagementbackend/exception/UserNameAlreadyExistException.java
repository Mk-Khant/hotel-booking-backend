package org.example.hotelmanagementbackend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.server.ResponseStatusException;

public class UserNameAlreadyExistException extends ResponseStatusException {

    public UserNameAlreadyExistException(String username) {
        super(HttpStatus.BAD_REQUEST,"%s already exists."
                .formatted(username));
    }
}
