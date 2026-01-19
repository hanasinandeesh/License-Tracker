package com.prodapt.license_tracker.software.exception;

public class SoftwareNotFoundException extends RuntimeException {

    public SoftwareNotFoundException(String message) {
        super(message);
    }
}
