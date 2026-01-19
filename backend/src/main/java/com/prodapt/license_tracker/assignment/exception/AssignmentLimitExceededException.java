package com.prodapt.license_tracker.assignment.exception;

public class AssignmentLimitExceededException extends RuntimeException {

    public AssignmentLimitExceededException(String message) {
        super(message);
    }
}
