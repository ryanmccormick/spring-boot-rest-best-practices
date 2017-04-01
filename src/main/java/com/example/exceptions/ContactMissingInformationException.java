package com.example.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by ryan on 3/31/17.
 */
@ResponseStatus(value = HttpStatus.UNPROCESSABLE_ENTITY, reason = "You must include a first name")
public class ContactMissingInformationException extends RuntimeException {
}
