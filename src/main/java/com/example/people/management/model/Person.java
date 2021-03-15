package com.example.people.management.model;

import com.example.people.management.exception.PeopleManagementAppException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import org.springframework.util.ObjectUtils;

@Entity
@Table(name = "PERSON")
public class Person {
    @Id
    @GeneratedValue
    private Integer id;
    private String firstName;
    private String lastName;

    @OneToMany(mappedBy = "person")
    private List<Address> addresses = new ArrayList<>();

    public Person() {

    }

    public Person(Integer id, String firstName, String lastName) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public Person(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public List<Address> getAddresses() {
        return Collections.unmodifiableList(addresses);
    }

    public void addAddress(Address address) {
        addresses.add(address);
    }

    public void validate() throws PeopleManagementAppException {
        StringBuilder errorMsgBuilder = new StringBuilder();
        if (ObjectUtils.isEmpty(firstName)) {
            errorMsgBuilder.append(" firstName");
        }
        if (ObjectUtils.isEmpty(lastName)) {
            errorMsgBuilder.append(" lastName");
        }
        if (errorMsgBuilder.length() > 0) {
            errorMsgBuilder.insert(0, "The following mandatory fields are empty:");
            throw new PeopleManagementAppException(errorMsgBuilder.toString());
        }
    }

    @Override
    public String toString() {
        return "personId=" + id + ", firstName=" + firstName + ", lastName=" + lastName;
    }
}
