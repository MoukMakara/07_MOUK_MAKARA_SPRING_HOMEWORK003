package org.ksga._07_mouk_makara_spring_homework003.exception;

public class VenueNotFoundException extends RuntimeException {
    public VenueNotFoundException(String message) {
        super(message);
    }
}