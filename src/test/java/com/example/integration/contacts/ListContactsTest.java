package com.example.integration.contacts;

import com.example.repository.ContactRepository;
import com.example.model.Contact;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;

import static junit.framework.TestCase.assertEquals;

/**
 * Created by ryan on 3/31/17.
 */

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ListContactsTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private ContactRepository contactRepository;

    @Autowired
    private TestHelper testHelper;

    private Contact singleContact;

    @Before
    public void addSingleContact() {
        Contact contact = new Contact();
        contact.setFirstName("Bob");
        contact.setLastName("Smith");
        contact.setPhone("555-555-5555");

        singleContact = contactRepository.saveAndFlush(contact);
    }

    @After
    public void afterAllTests() {
        contactRepository.delete(singleContact);
    }

    @Test
    public void listContacts() {
        ResponseEntity<Contact[]> responseEntity = restTemplate.exchange("/api/v1/contacts", HttpMethod.GET, testHelper.getRequestHeaders(), Contact[].class);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(MediaType.APPLICATION_JSON_UTF8, responseEntity.getHeaders().getContentType());
    }

    @Test
    public void getSingleContact() {
        String resourceUrl = "/api/v1/contacts/" + singleContact.getId();
        ResponseEntity<Contact> responseEntity = restTemplate.exchange(resourceUrl, HttpMethod.GET, testHelper.getRequestHeaders(), Contact.class);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(MediaType.APPLICATION_JSON_UTF8, responseEntity.getHeaders().getContentType());

        Contact parsedContact = responseEntity.getBody();
        assertEquals(singleContact.getId(), parsedContact.getId());
        assertEquals(singleContact.getFirstName(), parsedContact.getFirstName());
        assertEquals(singleContact.getLastName(), parsedContact.getLastName());
    }

    @Test
    public void handleNotFound() {
        String resourceUrl = "/api/v1/contacts/5555";
        ResponseEntity<Contact> responseEntity = restTemplate.exchange(resourceUrl, HttpMethod.GET, testHelper.getRequestHeaders(), Contact.class);

        assertEquals(404, responseEntity.getStatusCodeValue());
        assertEquals(MediaType.APPLICATION_JSON_UTF8, responseEntity.getHeaders().getContentType());
    }

}
