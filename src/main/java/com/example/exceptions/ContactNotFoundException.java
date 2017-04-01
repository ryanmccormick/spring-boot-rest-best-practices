package com.example.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by ryan on 3/31/17.
 */

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Contact not found")
public class ContactNotFoundException extends RuntimeException {
}
