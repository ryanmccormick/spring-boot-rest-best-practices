package com.example.service;

import com.example.repository.ContactRepository;
import com.example.exceptions.ContactMissingInformationException;
import com.example.exceptions.ContactNotFoundException;
import com.example.model.Contact;
import com.example.vo.SimpleContact;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.servlet.http.HttpServletRequest;
import java.util.AbstractMap;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by ryan on 3/31/17.
 */
@Service
public class ContactServiceImpl implements ContactService {

    private ContactRepository contactRepository;

    private ApiUtils apiUtils;

    @Autowired
    public ContactServiceImpl(ContactRepository contactRepository, ApiUtils apiUtils) {
        Assert.notNull(contactRepository, "ContactRepository must not be null!");
        Assert.notNull(apiUtils, "ApiUtils must not be null!");
        this.contactRepository = contactRepository;
        this.apiUtils = apiUtils;
    }


    @Override
    public ResponseEntity<List<SimpleContact>> getAllContactsResponse() {
        List<Contact> allContacts = contactRepository.findAll();
        List<SimpleContact> allSimpleContacts = allContacts.stream().map(objA -> {
            return new SimpleContact(objA);
        }).collect(Collectors.toList());

        return new ResponseEntity<List<SimpleContact>>(allSimpleContacts, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Contact> getSingleContactResponse(Long id) {
        Contact getContact = findContactIfExists(id);
        return new ResponseEntity<Contact>(getContact, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Contact> createNewContact(Contact contact, HttpServletRequest request) {

        if(null != contact.getFirstName() && contact.getFirstName().length() > 0) {
            Contact newContact = contactRepository.saveAndFlush(contact);
            HttpHeaders responseHeaders = new HttpHeaders();
            responseHeaders.set("Location", contactUrlHelper(newContact, request));

            return new ResponseEntity<Contact>(newContact, responseHeaders, HttpStatus.CREATED);
        } else {
            throw new ContactMissingInformationException();
        }
    }

    @Override
    public ResponseEntity<Contact> patchUpdateContact(Long id, Contact contactUpdates) {
        Contact existingContact = findContactIfExists(id);

        apiUtils.merge(existingContact, contactUpdates);

        // Ensure ID remains unchanged
        existingContact.setId(id);

        if(existingContact.getFirstName().length() > 0) {
            Contact updatedContact = contactRepository.saveAndFlush(existingContact);
            return new ResponseEntity<Contact>(updatedContact, HttpStatus.OK);
        } else {
            throw new ContactMissingInformationException();
        }
    }

    @Override
    public ResponseEntity<Contact> putUpdateContact(Long id, Contact contactUpdates) {
        Contact existingContact = findContactIfExists(id);

        if(null != contactUpdates.getFirstName() && contactUpdates.getFirstName().length() > 0) {
            BeanUtils.copyProperties(contactUpdates, existingContact);

            // Ensure ID remains unchanged
            existingContact.setId(id);

            Contact updatedContact = contactRepository.saveAndFlush(existingContact);
            return new ResponseEntity<Contact>(updatedContact, HttpStatus.OK);
        } else {
            throw new ContactMissingInformationException();
        }
    }

    @Override
    public ResponseEntity<Contact> deleteContact(Long id) {
        Contact existingContact = findContactIfExists(id);
        contactRepository.delete(id);
        return new ResponseEntity<Contact>(HttpStatus.NO_CONTENT);
    }

    /*
    * Private Methods
    */

    private String contactUrlHelper(Contact contact, HttpServletRequest request) {
        StringBuilder resourcePath = new StringBuilder();

        resourcePath.append(request.getRequestURL());
        resourcePath.append("/");
        resourcePath.append(contact.getId());

        return resourcePath.toString();
    }

    private Contact findContactIfExists(Long id) {
        Contact existingContact = contactRepository.findOne(id);

        if(null != existingContact) {
            return existingContact;
        } else {
            throw new ContactNotFoundException();
        }
    }


}
