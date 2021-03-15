package com.example.people.management.service;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.example.people.management.exception.PeopleManagementAppException;
import com.example.people.management.model.Address;
import com.example.people.management.model.Person;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class PersonServiceImplTest {
    @Autowired
    private PersonService personService;;

    private int invalidPersonId = 111;

    @Test
    void testUpdate_Invalid_PersonId() throws Exception {
        // Add person 1
        Person person = personService.addPerson(new Person("Ajith", "Sreekumar"));
        int personId1 = person.getId();
        assertThatThrownBy(() -> personService.editPerson(new Person(invalidPersonId, "Aji", "Sree")))
            .isInstanceOf(PeopleManagementAppException.class)
            .hasMessageContaining("Person with id " + invalidPersonId + " does not exist.");

        // now delete
        personService.deletePerson(personId1);
    }

    @Test
    void testAddAddress_nonExistingPersonId() throws Exception {
        // Add person 1
        Person person1 = personService.addPerson(new Person("Ajith", "Sreekumar"));
        int personId1 = person1.getId();
        assertThatThrownBy(() -> personService.addAddressToPerson(invalidPersonId,
            new Address("street22", "city22", "state22", "postalCode22")))
                .isInstanceOf(PeopleManagementAppException.class)
                .hasMessageContaining("Person with id " + invalidPersonId + " does not exist.");
        final Address address1 =
            personService.addAddressToPerson(personId1, new Address("street1", "city1", "state1", "postalCode1"));
        assertThatThrownBy(() -> personService.editAddress(invalidPersonId,
            new Address(address1.getId(), "street22", "city22", "state22", "postalCode22")))
                .isInstanceOf(PeopleManagementAppException.class)
                .hasMessageContaining("Person with id " + invalidPersonId + " does not exist.");
        personService.deletePerson(personId1);
    }

    @Test
    void testAddAddress_invalidPersonId() throws Exception {
        // Add person 1
        Person person1 = personService.addPerson(new Person("Ajith", "Sreekumar"));
        int personId1 = person1.getId();
        // Add person 2
        Person person2 = personService.addPerson(new Person("Steve", "Jobs"));
        int personId2 = person2.getId();
        final Address address1 =
            personService.addAddressToPerson(personId1, new Address("street1", "city1", "state1", "postalCode1"));
        final Address address2 =
            personService.addAddressToPerson(personId2, new Address("street2", "city2", "state2", "postalCode2"));
        // address2 does not belong to person1, so should throw exception
        assertThatThrownBy(() -> personService.editAddress(personId1,
            new Address(address2.getId(), "street22", "city22", "state22", "postalCode22")))
                .isInstanceOf(PeopleManagementAppException.class)
                .hasMessageContaining("Address with id " + address2.getId() + " doesn't belong to Person");
        personService.editAddress(personId1,
            new Address(address1.getId(), "street22", "city22", "state22", "postalCode22"));

        personService.deletePerson(personId1);
        personService.deletePerson(personId2);
    }
}

