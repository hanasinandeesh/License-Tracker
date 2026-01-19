package com.prodapt.license_tracker.license.exception;

public class LicenseLimitExceededException extends RuntimeException {

    public LicenseLimitExceededException(String message) {
        super(message);
    }
}
