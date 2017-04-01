package com.example.exceptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by ryan on 3/31/17.
 */

@ControllerAdvice
public class ExceptionHandlers {

    private Logger log = LoggerFactory.getLogger(ExceptionHandlers.class);

    @ExceptionHandler(ContactNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public ErrorResponse handleUserNotFoundException(final ContactNotFoundException ex) {
        log.error("Contact not found thrown");
        return new ErrorResponse("CONTACT_NOT_FOUND", "The contact was not found");
    }

    @ExceptionHandler(ContactMissingInformationException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    @ResponseBody
    public ErrorResponse handleContactMissingInformationException(final ContactNotFoundException ex) {
        log.error("Contact missing information thrown");
        return new ErrorResponse("MISSING_INFORMATION", "You must include a first name");
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Throwable.class)
    @ResponseBody
    public ErrorResponse handleThrowable(final Throwable ex) {
        log.error("Unexpected Error", ex);
        return new ErrorResponse("INTERNAL_SERVER_ERROR", "An unexpected internal server error occurred");
    }



}
