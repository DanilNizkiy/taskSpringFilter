package com.example.controller;

import static org.hamcrest.CoreMatchers.equalTo;
import example.model.Contact;
import example.service.repository.ContactRepository;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.assertj.core.api.Assertions.assertThat;

public class ContactControllerTest extends AbstractIntegrationTest {

    @Autowired
    private ContactRepository contactRepository;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void getContactsFromTheDatabaseUsingAfilter() throws Exception {

        contactRepository.save(new Contact("Danil"));
        contactRepository.save(new Contact("Denis"));
        contactRepository.save(new Contact("Anna"));
        contactRepository.save(new Contact("Boris"));
        assertThat(this.restTemplate.getForObject("/hello/contacts?nameFilter=^A.*$&responseSize=30",
                String.class)).contains("{\"id\":1,\"name\":\"Danil\"},{\"id\":2,\"name\":\"Denis\"},{\"id\":3,\"name\":\"Danil\"},{\"id\":4,\"name\":\"Denis\"},{\"id\":6,\"name\":\"Boris\"}]}");
    }

    @Test
    public void getDatabaseLength(){

        contactRepository.save(new Contact("Danil"));
        contactRepository.save(new Contact("Denis"));

        int updatedUsersSize = contactRepository.getDatabaseLength();
        assertThat(updatedUsersSize).isEqualTo(2);
    }

    @Test
    public void validationCheckStatusNotFound() throws Exception {

        assertThat(this.restTemplate.getForObject("/hello/contacts?nameFilter=^.*[aeqwesi].*$&responseSize=30",
                String.class)).contains("{\"error\":{\"code\":404,\"message\":\"Not Found\"}}");

    }


    @Test
    public void validationCheckHttpStatusOk(){

        ResponseEntity<String> response = restTemplate.
                getForEntity("/hello/contacts?nameFilter=^A.*$&responseSize=30", String.class);

        assertThat(response.getStatusCode(), equalTo(HttpStatus.OK));

    }

    @Test
    public void validationCheckHttpStatusBadRequest(){

        contactRepository.flush();
        ResponseEntity<String> response = restTemplate.
                getForEntity("/hello/contacts?nameFilter=^A.*$&respons567eSize=30", String.class);

        assertThat(response.getStatusCode(), equalTo(HttpStatus.BAD_REQUEST));

    }
}
