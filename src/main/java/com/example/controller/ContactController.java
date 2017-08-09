package com.example.controller;

import com.example.model.Contact;
import com.example.service.ContactService;
import com.example.vo.SimpleContact;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by ryan on 3/31/17.
 */

@RestController
@RequestMapping("api/v1/")
public class ContactController {

    @Autowired
    private ContactService contactService;

    // List All Contacts
    @RequestMapping(value = "contacts", method = RequestMethod.GET)
    public ResponseEntity<List<SimpleContact>> getAllContacts() throws Throwable {
        return contactService.getAllContactsResponse();
    }

    // List One Contact
    @RequestMapping(value = "contacts/{id}", method = RequestMethod.GET)
    public ResponseEntity<Contact> getSingleContact(@PathVariable Long id) throws Throwable {
        return contactService.getSingleContactResponse(id);
    }

    // Create New Contact
    @RequestMapping(value = "contacts", method = RequestMethod.POST)
    public ResponseEntity<Contact> createNewContact(@RequestBody Contact contact, HttpServletRequest req) {
        return contactService.createNewContact(contact, req);
    }

    // Update Contact with PATCH
    @RequestMapping(value = "contacts/{id}", method = RequestMethod.PATCH)
    public ResponseEntity<Contact> patchUpdateContact(@PathVariable Long id, @RequestBody Contact contact) {
        return contactService.patchUpdateContact(id, contact);
    }

    // Update Contact with PUT
    @RequestMapping(value = "contacts/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Contact> putUpdateContact(@PathVariable Long id, @RequestBody Contact contact) {
        return contactService.putUpdateContact(id, contact);
    }

    // Delete Contact
    @RequestMapping(value = "contacts/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Contact> deleteContact(@PathVariable Long id) {
        return contactService.deleteContact(id);
    }



}
