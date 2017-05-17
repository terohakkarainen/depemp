package fi.thakki.depemp.controller;

import org.springframework.validation.Errors;

public class ValidationFailedException extends RuntimeException {

    private Errors myErrors;

    public ValidationFailedException(
            Errors errors) {
        myErrors = errors;
    }

    public Errors getErrors() {
        return myErrors;
    }
}