package com.example.service;

import com.example.Repository.ContactRepository;
import com.example.exceptions.ContactMissingInformationException;
import com.example.exceptions.ContactNotFoundException;
import com.example.model.Contact;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by ryan on 3/31/17.
 */
@Service
public class ContactServiceImpl implements ContactService {

    @Autowired
    private ContactRepository contactRepository;

    @Autowired
    private ApiUtils apiUtils;

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
        Contact existingContact = contactRepository.findOne(id);

        if(null != existingContact) {
            if(null != contactUpdates.getFirstName() && contactUpdates.getFirstName().length() > 0) {
                apiUtils.merge(existingContact, contactUpdates);

                // Ensure ID remains unchanged
                existingContact.setId(id);

                Contact updatedContact = contactRepository.saveAndFlush(existingContact);
                return new ResponseEntity<Contact>(updatedContact, HttpStatus.OK);
            } else {
                throw new ContactMissingInformationException();
            }
        } else {
            throw new ContactNotFoundException();
        }
    }

    @Override
    public ResponseEntity<Contact> putUpdateContact(Long id, Contact contactUpdates) {
        Contact existingContact = contactRepository.findOne(id);

        if(null != existingContact) {
            if(null != contactUpdates.getFirstName() && contactUpdates.getFirstName().length() > 0) {
                BeanUtils.copyProperties(contactUpdates, existingContact);

                // Ensure ID remains unchanged
                existingContact.setId(id);

                Contact updatedContact = contactRepository.saveAndFlush(existingContact);
                return new ResponseEntity<Contact>(updatedContact, HttpStatus.OK);
            } else {
                throw new ContactMissingInformationException();
            }
        } else {
            throw new ContactNotFoundException();
        }
    }

    @Override
    public ResponseEntity<Contact> deleteContact(Long id) {
        Contact existingContact = contactRepository.findOne(id);

        if(null != existingContact) {
            contactRepository.delete(id);
            return new ResponseEntity<Contact>(HttpStatus.NO_CONTENT);
        } else {
            throw new ContactNotFoundException();
        }
    }

    @Override
    public String contactUrlHelper(Contact contact, HttpServletRequest request) {
        StringBuilder resourcePath = new StringBuilder();

        resourcePath.append(request.getRequestURL());
        resourcePath.append("/");
        resourcePath.append(contact.getId());

        return resourcePath.toString();
    }
}
