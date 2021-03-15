package com.example.people.management.controller;

import com.example.people.management.model.Address;
import com.example.people.management.model.Person;
import com.example.people.management.service.PersonService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(path = "/demo")
public class ApplicationController {
    @Autowired
    private PersonService personService;

    @PostMapping(path = "/person")
    public @ResponseBody ResponseEntity<Object> addNewPerson(@RequestBody Person person) {
        try {
            personService.addPerson(person);
            return ResponseEntity.ok().body(person);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PutMapping(path = "/person")
    public @ResponseBody ResponseEntity<Object> editPerson(@RequestBody Person person) {
        try {
            personService.editPerson(person);
            return ResponseEntity.ok().body(person);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @DeleteMapping(path = "/person/{personId}")
    public @ResponseBody ResponseEntity<Object> deletePerson(@PathVariable int personId) {
        try {
            String result = personService.deletePerson(personId);
            return ResponseEntity.ok().body(result);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PostMapping(path = "/person/{personId}/address")
    public @ResponseBody ResponseEntity<Object> addAddressToPerson(@PathVariable int personId,
        @RequestBody Address address) {
        try {
            personService.addAddressToPerson(personId, address);
            return ResponseEntity.ok().body(address);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PutMapping(path = "/person/{personId}/address")
    public @ResponseBody ResponseEntity<Object> editAddress(@PathVariable int personId,
        @RequestBody Address address) {
        try {
            personService.editAddress(personId, address);
            return ResponseEntity.ok().body(address);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @DeleteMapping(path = "/address/{addressId}")
    public @ResponseBody ResponseEntity<Object> deleteAddress(@PathVariable int addressId) {
        try {
            String result = personService.deleteAddress(addressId);
            return ResponseEntity.ok().body(result);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping(path = "/person/list")
    public @ResponseBody ResponseEntity<Object> getPeopleList() {
        try {
            List<Person> result = personService.listPeople();
            return ResponseEntity.ok().body(result);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping(path = "/person/count")
    public @ResponseBody ResponseEntity<Object> getPeopleCount() {

        try {
            long result = personService.countNumberOfPeople();
            return ResponseEntity.ok().body(result);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}
