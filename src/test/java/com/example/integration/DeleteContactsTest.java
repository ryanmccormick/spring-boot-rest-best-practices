package com.example.integration;

import com.example.Repository.ContactRepository;
import com.example.model.Contact;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import static junit.framework.TestCase.assertEquals;

/**
 * Created by ryan on 3/31/17.
 */

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class DeleteContactsTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private ContactRepository contactRepository;

    @Autowired
    private TestHelper testHelper;

    private Contact contact1;

    @Before
    public void setup() {
        contact1 = contactRepository.saveAndFlush(new Contact("Bob", "Smith", "555-555-5555"));
    }

    @After
    public void afterAllTests() {
        contactRepository.delete(contact1);
    }

    @Test
    public void deleteContactOne() {
        String resourceUrl = "/api/v1/contacts/" + contact1.getId().toString();

        ResponseEntity<Contact> responseEntity = restTemplate.exchange(resourceUrl, HttpMethod.DELETE, testHelper.getRequestHeaders(), Contact.class);

        // Check for proper status
        assertEquals(204, responseEntity.getStatusCodeValue());
        //assertEquals(MediaType.APPLICATION_JSON_UTF8, responseEntity.getHeaders().getContentType());
    }

    @Test
    public void deleteNonExistantContact() {
        String resourceUrl = "/api/v1/contacts/5555";

        ResponseEntity<Contact> responseEntity = restTemplate.exchange(resourceUrl, HttpMethod.DELETE, testHelper.getRequestHeaders(), Contact.class);

        // Check for proper status
        assertEquals(404, responseEntity.getStatusCodeValue());
        assertEquals(MediaType.APPLICATION_JSON_UTF8, responseEntity.getHeaders().getContentType());
    }


}
