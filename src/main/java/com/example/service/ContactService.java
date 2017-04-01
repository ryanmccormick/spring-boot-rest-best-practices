package com.example.service;

import com.example.model.Contact;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by ryan on 3/31/17.
 */

public interface ContactService {

    public ResponseEntity<List<Contact>> getAllContactsResponse();
    public ResponseEntity<Contact> getSingleContactResponse(Long id);


}
