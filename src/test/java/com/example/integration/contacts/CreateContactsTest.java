package com.example.integration.contacts;

import com.example.Repository.ContactRepository;
import com.example.model.Contact;

import org.json.JSONObject;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertThat;
import static org.hamcrest.CoreMatchers.containsString;

/**
 * Created by ryan on 3/31/17.
 */

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CreateContactsTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private ContactRepository contactRepository;

    @Autowired
    private TestHelper testHelper;

    private Contact createdContact;

    @After
    public void cleanup() {
        if(null != createdContact) {
            contactRepository.delete(createdContact);
        }
    }

    @Test
    public void createNewContact() {
        String resourceUrl = "/api/v1/contacts";
        String firstName = "Bob";
        String lastName = "Smith";
        String phone = "555-555-5555";

        JSONObject postBody = testHelper.constructContact(firstName, lastName, phone);

        ResponseEntity<Contact> responseEntity =
                restTemplate.exchange(resourceUrl, HttpMethod.POST, testHelper.getPostRequestHeaders(postBody.toString()), Contact.class);

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals(MediaType.APPLICATION_JSON_UTF8, responseEntity.getHeaders().getContentType());

        createdContact = responseEntity.getBody();
        assertEquals(firstName, createdContact.getFirstName());
        assertEquals(lastName, createdContact.getLastName());
        assertEquals(phone, createdContact.getPhone());

        // Check Location HeaderURL
        String expectedLocationUrl = testHelper.contactUrlHelper(resourceUrl, createdContact.getId().toString());
        String returnedLocationUrl = responseEntity.getHeaders().getLocation().toString();

        assertThat(returnedLocationUrl, containsString(expectedLocationUrl));
    }

    @Test
    public void createNewContactWithoutFirstName() {
        String resourceUrl = "/api/v1/contacts";
        String lastName = "Smith";
        String phone = "555-555-5555";

        JSONObject postBody = testHelper.constructContact(null, lastName, phone);

        ResponseEntity<Contact> responseEntity =
                restTemplate.exchange(resourceUrl, HttpMethod.POST, testHelper.getPostRequestHeaders(postBody.toString()), Contact.class);

        assertEquals(422, responseEntity.getStatusCodeValue());
        assertEquals(MediaType.APPLICATION_JSON_UTF8, responseEntity.getHeaders().getContentType());
    }

    @Test
    public void createNewContactWithBlankFirstName() {
        String resourceUrl = "/api/v1/contacts";
        String firstName = "";
        String lastName = "Smith";
        String phone = "555-555-5555";

        JSONObject postBody = testHelper.constructContact(firstName, lastName, phone);

        ResponseEntity<Contact> responseEntity =
                restTemplate.exchange(resourceUrl, HttpMethod.POST, testHelper.getPostRequestHeaders(postBody.toString()), Contact.class);

        assertEquals(422, responseEntity.getStatusCodeValue());
        assertEquals(MediaType.APPLICATION_JSON_UTF8, responseEntity.getHeaders().getContentType());
    }

}
