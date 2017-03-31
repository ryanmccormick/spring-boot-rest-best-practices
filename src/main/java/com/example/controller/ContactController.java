package com.example.controller;

import com.example.Repository.ContactRepository;
import com.example.model.Contact;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by ryan on 3/31/17.
 */

@RestController
@RequestMapping("api/v1/")
public class ContactController {

    @Autowired
    private ContactRepository contactRepository;


    @RequestMapping(value = "contacts", method = RequestMethod.GET)
    public ResponseEntity<List<Contact>> getAllContacts() {
        List<Contact> allContacts = contactRepository.findAll();
        return new ResponseEntity<List<Contact>>(allContacts, HttpStatus.OK);
    }


}
