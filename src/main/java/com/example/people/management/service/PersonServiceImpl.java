package com.example.people.management.service;

import com.example.people.management.exception.PeopleManagementAppException;
import com.example.people.management.model.Address;
import com.example.people.management.model.Person;
import com.example.people.management.repository.PersonRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

@Service("personService")
public class PersonServiceImpl implements PersonService {

    private static final String DOES_NOT_EXIST = " does not exist.";
    private static final String ADDRESS_WITH_ID = "Address with id ";
    private static final String DELETED_SUCCESSFULLY = " deleted successfully.";
    private static final String PERSON_WITH_ID = "Person with id ";
    @Autowired
    private PersonRepository repository;

    public PersonServiceImpl() {
        // the no-arg constructor
    }

    @Override
    public Person addPerson(Person person) throws PeopleManagementAppException {
        if (null != person.getId()) {
            throw new PeopleManagementAppException(
                "Id is generated once you create a Person, Use PUT method to update an existing person.");
        }
        person.validate();
        return repository.addPerson(person);
    }

    @Override
    public Person editPerson(Person person) throws PeopleManagementAppException {
        getPersonById(person.getId()); // will throw exception if id is not existing.
        person.validate();
        return repository.editPerson(person);
    }

    @Override
    public String deletePerson(int personId) throws PeopleManagementAppException {
        getPersonById(personId); // will throw exception if id is not existing.
        repository.deletePerson(personId);
        return PERSON_WITH_ID + personId + DELETED_SUCCESSFULLY;
    }

    @Override
    public Address addAddressToPerson(int personId, Address address) throws PeopleManagementAppException {
        if (null != address.getId()) {
            throw new PeopleManagementAppException(
                "Id is generated once you create an Address, Use PUT method to update an existing address.");
        }
        Person person = getPersonById(personId);
        address.setPerson(person);
        address.validate();
        return repository.addAddress(address);
    }

    @Override
    public Address editAddress(int personId, Address address) throws PeopleManagementAppException {
        Person person = getPersonById(personId);
        Integer addressId = address.getId();
        if (null == addressId) {
            throw new PeopleManagementAppException("Address id must be specified in the address body to edit it.");
        }
        Optional<Address> addressOpt =
            person.getAddresses().parallelStream().filter(addr -> addr.getId().equals(address.getId())).findFirst();
        if (addressOpt.isEmpty()) {
            throw new PeopleManagementAppException(
                ADDRESS_WITH_ID + addressId + " doesn't belong to Person: " + person.toString());
        }
        address.setPerson(person);
        address.validate();
        return repository.editAddress(address);
    }

    @Override
    public String deleteAddress(int addressId) throws PeopleManagementAppException {
        if (repository.deleteAddress(addressId) == 0) {
            throw new PeopleManagementAppException(ADDRESS_WITH_ID + addressId + DOES_NOT_EXIST);
        }
        return ADDRESS_WITH_ID + addressId + DELETED_SUCCESSFULLY;
    }

    @Override
    public long countNumberOfPeople() {
        return repository.countNumberOfPeople();
    }

    @Override
    public List<Person> listPeople() {
        return repository.listPeople();
    }

    private Person getPersonById(int personId) throws PeopleManagementAppException {
        Person person;
        try {
            person = repository.getPersonById(personId);
        } catch (DataAccessException exc) {
            throw new PeopleManagementAppException(PERSON_WITH_ID + personId + DOES_NOT_EXIST, exc);
        }
        return person;
    }

}
