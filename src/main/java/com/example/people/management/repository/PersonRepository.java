package com.example.people.management.repository;

import com.example.people.management.model.Address;
import com.example.people.management.model.Person;
import java.util.List;

public interface PersonRepository {
    public Person addPerson(Person person);
    public Person editPerson(Person person);
    public int deletePerson(int personId);
    public Address addAddress(Address address);
    public Address editAddress(Address address);
    public int deleteAddress(int addressId);
    public long countNumberOfPeople();
    public List<Person> listPeople();
    public Person getPersonById(int personId);
}
