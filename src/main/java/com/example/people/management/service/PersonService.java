package com.example.people.management.service;

import com.example.people.management.exception.PeopleManagementAppException;
import com.example.people.management.model.Address;
import com.example.people.management.model.Person;
import java.util.List;

public interface PersonService {
    public Person addPerson(Person person) throws PeopleManagementAppException;

    public Person editPerson(Person person) throws PeopleManagementAppException;

    public String deletePerson(int personId) throws PeopleManagementAppException;

    public Address addAddressToPerson(int personId, Address address) throws PeopleManagementAppException;

    public Address editAddress(int personId, Address address) throws PeopleManagementAppException;

    public String deleteAddress(int addressId) throws PeopleManagementAppException;

    public long countNumberOfPeople() throws PeopleManagementAppException;

    public List<Person> listPeople() throws PeopleManagementAppException;
}
