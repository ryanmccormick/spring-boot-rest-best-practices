package com.example.service;

import com.example.Repository.ContactRepository;
import com.example.exceptions.ContactNotFoundException;
import com.example.model.Contact;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by ryan on 3/31/17.
 */
@Service
public class ContactServiceImpl implements ContactService {

    @Autowired
    private ContactRepository contactRepository;

    @Override
    public ResponseEntity<List<Contact>> getAllContactsResponse() {
        List<Contact> allContacts = contactRepository.findAll();
        return new ResponseEntity<List<Contact>>(allContacts, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Contact> getSingleContactResponse(Long id) {
        Contact getContact = contactRepository.findOne(id);

        if(null != getContact) {
            return new ResponseEntity<Contact>(getContact, HttpStatus.OK);
        } else {
            throw new ContactNotFoundException();
        }

    }
}
