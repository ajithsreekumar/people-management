package com.example.people.management.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.example.people.management.model.Address;
import com.example.people.management.model.Person;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class ApplicationControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void testUpdate_Person() throws Exception {
        // Add person
        Person person = addPerson("Ajith", "Sreekumar");
        assertThat(person).isNotNull();
        int personId = person.getId();
        assertThat(personId).isGreaterThan(0);
        assertEquals("Ajith", person.getFirstName());
        assertEquals("Sreekumar", person.getLastName());
        assertThat(person.getAddresses()).isEmpty();

        // List people
        List<Person> people = listPeople();
        assertThat(people.size()).isEqualTo(1);

        // Count number of people
        long count = countPeople();
        assertThat(count).isEqualTo(1);
        // Edit person
        person = editPerson(personId, "Aji", "Sree");
        assertThat(person).isNotNull();
        assertEquals("Aji", person.getFirstName());
        assertEquals("Sree", person.getLastName());

        // Delete person
        deletePerson(personId);
        people = listPeople();
        assertThat(people.size()).isEqualTo(0);
        count = countPeople();
        assertThat(count).isEqualTo(0);
    }

    @Test
    void testUpdate_Multiple_Person() throws Exception {
        // Add person 1
        Person person1 = addPerson("Ajith", "Sreekumar");
        int personId1 = person1.getId();

        // Add person 2
        Person person2 = addPerson("Steve", "Jobs");
        int personId2 = person2.getId();
        long count = countPeople();
        assertThat(count).isEqualTo(2);
        // now delete
        deletePerson(personId1);
        count = countPeople();
        assertThat(count).isEqualTo(1);
        deletePerson(personId2);
        count = countPeople();
        assertThat(count).isEqualTo(0);
    }

    @Test
    void testUpdate_Address() throws Exception {
        // Add person 1
        Person person1 = addPerson("Ajith", "Sreekumar");
        int personId1 = person1.getId();
        // Add person 2
        Person person2 = addPerson("Steve", "Jobs");
        int personId2 = person2.getId();

        Address address1 = addAddress(personId1, "street1", "city1", "state1", "postalCode1");
        Address address2 = addAddress(personId2, "street2", "city2", "state2", "postalCode2");
        assertEquals("street1", address1.getStreet());
        int addressId = address1.getId();
        assertThat(addressId).isGreaterThan(0);

        assertEquals("state2", address2.getState());
        addressId = address2.getId();
        assertThat(addressId).isGreaterThan(0);

        address2 = editAddress(personId2, addressId, "street22", "city22", "state22", "postalCode22");
        assertEquals("state22", address2.getState());

        deleteAddress(addressId);

        long count = countPeople();
        assertThat(count).isEqualTo(2);
        // now delete
        deletePerson(personId1);
        deletePerson(personId2);
        count = countPeople();
        assertThat(count).isEqualTo(0);
    }

    private Person addPerson(String firstName, String lastName) {
        return this.restTemplate.postForObject(
            "http://localhost:" + port + "/demo/person?firstName={firstName}&lastName={lastName}", null, Person.class,
            firstName, lastName);
    }

    private Person editPerson(int personId, String firstName, String lastName) {
        return this.restTemplate.postForObject(
            "http://localhost:" + port + "/demo/person/{personId}?firstName={firstName}&lastName={lastName}", null,
            Person.class, personId, firstName, lastName);
    }

    private void deletePerson(int personId) throws Exception {
        this.restTemplate.delete("http://localhost:" + port + "/demo/person/{personId}", personId);
    }

    private List<Person> listPeople() {
        return this.restTemplate.getForObject("http://localhost:" + port + "/demo/person/list", List.class);
    }

    private long countPeople() {
        return this.restTemplate.getForObject("http://localhost:" + port + "/demo/person/count", long.class);
    }

    private Address addAddress(int personId, String street, String city, String state, String postalCode) {
        return this.restTemplate.postForObject(
            "http://localhost:" + port
                + "/demo/person/{personId}/address?street={street}&city={city}&state={state}&postalCode={postalCode}",
            null, Address.class, personId, street, city, state, postalCode);
    }

    private Address editAddress(int personId, int addressId, String street, String city, String state,
        String postalCode) {
        return this.restTemplate.postForObject("http://localhost:" + port
            + "/demo/person/{personId}/address/{addressId}?street={street}&city={city}&state={state}&postalCode={postalCode}",
            null, Address.class, personId, addressId, street, city, state, postalCode);
    }

    private void deleteAddress(int addressId) throws Exception {
        this.restTemplate.delete("http://localhost:" + port + "/demo/address/{addressId}", addressId);
    }
}
