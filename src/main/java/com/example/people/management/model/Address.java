package com.example.people.management.model;

import com.example.people.management.exception.PeopleManagementAppException;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import org.springframework.util.ObjectUtils;

@Entity
@Table(name = "ADDRESS")
public class Address {
    @Id
    @GeneratedValue
    private Integer id;
    private String street;
    private String city;
    private String state;
    private String postalCode;

    @ManyToOne
    @JoinColumn(name = "PERSON_ID")
    private Person person;

    public Address() {

    }

    public Address(Integer id, String street, String city, String state, String postalCode) {
        this.id = id;
        this.street = street;
        this.city = city;
        this.state = state;
        this.postalCode = postalCode;
    }

    public Address(String street, String city, String state, String postalCode) {
        this.street = street;
        this.city = city;
        this.state = state;
        this.postalCode = postalCode;
    }
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public void validate() throws PeopleManagementAppException {
        StringBuilder errorMsgBuilder = new StringBuilder();
        if (ObjectUtils.isEmpty(street)) {
            errorMsgBuilder.append(" street");
        }
        if (ObjectUtils.isEmpty(city)) {
            errorMsgBuilder.append(" city");
        }
        if (ObjectUtils.isEmpty(state)) {
            errorMsgBuilder.append(" state");
        }
        if (ObjectUtils.isEmpty(postalCode)) {
            errorMsgBuilder.append(" postalCode");
        }
        if (errorMsgBuilder.length() > 0) {
            errorMsgBuilder.insert(0, "The following mandatory fields are empty:");
            throw new PeopleManagementAppException(errorMsgBuilder.toString());
        }
    }

    @Override
    public String toString(){
        return "addressId="+id+", street="+street+", city="+city+", state="+state+", postalCode="+postalCode+", personId="+person.getId();
    }
}
