package com.example.service;

import com.example.model.Contact;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by ryan on 3/31/17.
 */

public interface ContactService {
    public ResponseEntity<List<Contact>> getAllContactsResponse();
    public ResponseEntity<Contact> getSingleContactResponse(Long id);
    public ResponseEntity<Contact> createNewContact(Contact contact, HttpServletRequest request);
    public ResponseEntity<Contact> patchUpdateContact(Long id, Contact contactUpdates);
    public ResponseEntity<Contact> putUpdateContact(Long id, Contact contactUpdates);
    public ResponseEntity<Contact> deleteContact(Long id);
}
