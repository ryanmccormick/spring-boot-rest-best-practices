package com.example.integration;

import com.example.Repository.ContactRepository;
import com.example.model.Contact;
import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Created by ryan on 3/31/17.
 */

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UpdateContactsTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private ContactRepository contactRepository;

    @Autowired
    private TestHelper testHelper;

    private Contact contact1;
    private Contact contact2;
    private Contact contact3;

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

}
