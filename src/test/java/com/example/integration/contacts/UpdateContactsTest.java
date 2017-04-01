package com.example.integration.contacts;

import com.example.Repository.ContactRepository;
import com.example.model.Contact;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.json.JSONObject;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertThat;

/**
 * Created by ryan on 3/31/17.
 */

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UpdateContactsTest {

    @Autowired
    private TestRestTemplate restTemplate;

    private RestTemplate patchRestTemplate;

    @Autowired
    private ContactRepository contactRepository;

    @Autowired
    private TestHelper testHelper;

    private Contact contact1;
    private Contact contact2;

    @Before
    public void setup() {
        contact1 = contactRepository.saveAndFlush(new Contact("Bob", "Smith", "555-555-5555"));
        contact2 = contactRepository.saveAndFlush(new Contact("Marty", "McFly", "555-555-5555"));

        // Setup RestTemplate for PATCH Action
        // TODO: Create blog post
        this.patchRestTemplate = restTemplate.getRestTemplate();
        HttpClient httpClient = HttpClientBuilder.create().build();
        this.patchRestTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory(httpClient));
    }

    @After
    public void afterAllTests() {
        contactRepository.delete(contact1);
        contactRepository.delete(contact2);
    }

    @Test
    public void updateContactOneWithPatch() throws Throwable {
        String resourceUrl = "/api/v1/contacts/" + contact1.getId().toString();

        JSONObject updateBody = new JSONObject();
        updateBody.put("firstName", "Robert");

        ResponseEntity<Contact> responseEntity =
                patchRestTemplate.exchange(resourceUrl, HttpMethod.PATCH, testHelper.getPostRequestHeaders(updateBody.toString()), Contact.class);

        // Check for proper status
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(MediaType.APPLICATION_JSON_UTF8, responseEntity.getHeaders().getContentType());

        // Check for proper body
        Contact updatedContact = responseEntity.getBody();
        assertEquals("Robert", updatedContact.getFirstName());
        assertEquals("Smith", updatedContact.getLastName());
    }

    @Test
    public void updateContactOneWithPatchAndInvalidFirstName() throws Throwable {
        String resourceUrl = "/api/v1/contacts/" + contact1.getId().toString();

        JSONObject updateBody = new JSONObject();
        updateBody.put("firstName", "");

        ResponseEntity<Contact> responseEntity =
                patchRestTemplate.exchange(resourceUrl, HttpMethod.PATCH, testHelper.getPostRequestHeaders(updateBody.toString()), Contact.class);

        // Check for proper status
        assertEquals(422, responseEntity.getStatusCodeValue());
        assertEquals(MediaType.APPLICATION_JSON_UTF8, responseEntity.getHeaders().getContentType());
    }

    @Test
    public void updateContactOnePhoneNumberWithPatch() throws Throwable {
        String resourceUrl = "/api/v1/contacts/" + contact1.getId().toString();

        JSONObject updateBody = new JSONObject();
        updateBody.put("phone", "123456");

        ResponseEntity<Contact> responseEntity =
                patchRestTemplate.exchange(resourceUrl, HttpMethod.PATCH, testHelper.getPostRequestHeaders(updateBody.toString()), Contact.class);

        // Check for proper status
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(MediaType.APPLICATION_JSON_UTF8, responseEntity.getHeaders().getContentType());

        // Check for proper body
        Contact updatedContact = responseEntity.getBody();
        assertEquals("Smith", updatedContact.getLastName());
        assertEquals("123456", updatedContact.getPhone());
    }


    @Test
    public void updateContactTwoWithPut() throws Throwable {
        String resourceUrl = "/api/v1/contacts/" + contact2.getId().toString();

        JSONObject updateBody = new JSONObject();
        updateBody.put("firstName", "Martin");
        updateBody.put("lastName", "McFly");

        ResponseEntity<Contact> responseEntity =
                patchRestTemplate.exchange(resourceUrl, HttpMethod.PUT, testHelper.getPostRequestHeaders(updateBody.toString()), Contact.class);

        // Check for proper status
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(MediaType.APPLICATION_JSON_UTF8, responseEntity.getHeaders().getContentType());

        // Check for proper body
        Contact updatedContact = responseEntity.getBody();
        assertEquals("Martin", updatedContact.getFirstName());
        assertEquals("McFly", updatedContact.getLastName());
        assertEquals(null, updatedContact.getPhone());
    }

    @Test
    public void updateContactTwoWithPutAndInvalidFirstName() throws Throwable {
        String resourceUrl = "/api/v1/contacts/" + contact2.getId().toString();

        JSONObject updateBody = new JSONObject();
        updateBody.put("firstName", "");
        updateBody.put("lastName", "McFly");

        ResponseEntity<Contact> responseEntity =
                restTemplate.exchange(resourceUrl, HttpMethod.PUT, testHelper.getPostRequestHeaders(updateBody.toString()), Contact.class);

        // Check for proper status
        assertEquals(422, responseEntity.getStatusCodeValue());
        assertEquals(MediaType.APPLICATION_JSON_UTF8, responseEntity.getHeaders().getContentType());
    }

    @Test
    public void patchUpdateNonExistantContact() throws Throwable {
        String resourceUrl = "/api/v1/contacts/5555";

        JSONObject updateBody = new JSONObject();
        updateBody.put("firstName", "Jack");

        ResponseEntity<Contact> responseEntity =
                patchRestTemplate.exchange(resourceUrl, HttpMethod.PATCH, testHelper.getPostRequestHeaders(updateBody.toString()), Contact.class);

        // Check for proper status
        assertEquals(404, responseEntity.getStatusCodeValue());
        assertEquals(MediaType.APPLICATION_JSON_UTF8, responseEntity.getHeaders().getContentType());
    }

    @Test
    public void putUpdateNonExistantContact() throws Throwable {
        String resourceUrl = "/api/v1/contacts/7777";

        JSONObject updateBody = new JSONObject();
        updateBody.put("firstName", "John");

        ResponseEntity<Contact> responseEntity =
                patchRestTemplate.exchange(resourceUrl, HttpMethod.PUT, testHelper.getPostRequestHeaders(updateBody.toString()), Contact.class);

        // Check for proper status
        assertEquals(404, responseEntity.getStatusCodeValue());
        assertEquals(MediaType.APPLICATION_JSON_UTF8, responseEntity.getHeaders().getContentType());
    }

}
