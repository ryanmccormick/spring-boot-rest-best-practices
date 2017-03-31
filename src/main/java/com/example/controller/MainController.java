package com.example.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by ryan on 3/31/17.
 */
@RestController
public class MainController {

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ResponseEntity rootResponse() {
        String welcomeMessage = "Welcome to the spring boot integration test sample api";
        return new ResponseEntity<String>(welcomeMessage, HttpStatus.OK);
    }

}
