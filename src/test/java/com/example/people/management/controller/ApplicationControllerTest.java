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
        Person person = addPerson(new Person("Ajith", "Sreekumar"));
        assertThat(person).isNotNull();
        Integer personId = person.getId();
        assertThat(personId).isNotNull();
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
        editPerson(new Person(personId, "Aji", "Sree"));

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
        Person person1 = addPerson(new Person("Ajith", "Sreekumar"));
        Integer personId1 = person1.getId();

        // Add person 2
        Person person2 = addPerson(new Person("Steve", "Jobs"));
        Integer personId2 = person2.getId();
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
        Person person1 = addPerson(new Person("Ajith", "Sreekumar"));
        Integer personId1 = person1.getId();
        // Add person 2
        Person person2 = addPerson(new Person("Steve", "Jobs"));
        Integer personId2 = person2.getId();

        Address address1 = addAddress(personId1, new Address("street1", "city1", "state1", "postalCode1"));
        Address address2 = addAddress(personId2, new Address("street2", "city2", "state2", "postalCode2"));
        assertEquals("street1", address1.getStreet());
        Integer addressId = address1.getId();
        assertThat(addressId).isNotNull();

        assertEquals("state2", address2.getState());
        addressId = address2.getId();
        assertThat(addressId).isNotNull();

        editAddress(personId2, new Address(addressId, "street22", "city22", "state22", "postalCode22"));
        deleteAddress(addressId);

        long count = countPeople();
        assertThat(count).isEqualTo(2);
        // now delete
        deletePerson(personId1);
        deletePerson(personId2);
        count = countPeople();
        assertThat(count).isEqualTo(0);
    }

    private Person addPerson(Person person) {
        return this.restTemplate.postForObject("http://localhost:" + port + "/demo/person", person, Person.class);
    }

    private void editPerson(Person person) {
        this.restTemplate.put("http://localhost:" + port + "/demo/person", person);
    }

    private void deletePerson(Integer personId) throws Exception {
        this.restTemplate.delete("http://localhost:" + port + "/demo/person/{personId}", personId);
    }

    private List<Person> listPeople() {
        return this.restTemplate.getForObject("http://localhost:" + port + "/demo/person/list", List.class);
    }

    private long countPeople() {
        return this.restTemplate.getForObject("http://localhost:" + port + "/demo/person/count", long.class);
    }

    private Address addAddress(Integer personId, Address address) {
        return this.restTemplate.postForObject("http://localhost:" + port + "/demo/person/{personId}/address", address,
            Address.class, personId);
    }

    private void editAddress(Integer personId, Address address) {
        this.restTemplate.put("http://localhost:" + port + "/demo/person/{personId}/address", address, Address.class,
            personId);
    }

    private void deleteAddress(Integer addressId) throws Exception {
        this.restTemplate.delete("http://localhost:" + port + "/demo/address/{addressId}", addressId);
    }
}

