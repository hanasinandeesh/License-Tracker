package com.prodapt.license_tracker.license.exception;

public class LicenseNotFoundException extends RuntimeException {

    public LicenseNotFoundException(String message) {
        super(message);
    }
}
