package com.example.people.management.repository;

import com.example.people.management.model.Address;
import com.example.people.management.model.Person;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;
import org.springframework.stereotype.Repository;

@Transactional
@Repository("personRepository")
public class HibernatePersonRepositoryImpl implements PersonRepository {

    @PersistenceContext
    private EntityManager em;

    @Override
    public Person addPerson(Person person) {
        em.persist(person);
        return person;
    }

    @Override
    public Person editPerson(Person person) {
        em.merge(person);
        return person;
    }

    @Override
    public int deletePerson(int personId) {
        Query query = em.createQuery("select p from Person p left join fetch p.addresses where p.id = :id");
        query.setParameter("id", personId);
        Person person = (Person) query.getSingleResult();
        person.getAddresses().forEach(address -> deleteAddress(address.getId()));
        query = em.createQuery("delete from Person p where p.id = :id");
        query.setParameter("id", personId);
        return query.executeUpdate();
    }

    @Override
    public List<Person> listPeople() {
        List<Person> people =
            em.createQuery("select distinct p from Person p left join fetch p.addresses").getResultList();
        return people;
    }

    @Override
    public Person getPersonById(int personId) {
        Query query = em.createQuery("select p from Person p left join fetch p.addresses where p.id = :id");
        query.setParameter("id", personId);
        return (Person) query.getSingleResult();
    }

    @Override
    public Address addAddress(Address address) {
        em.persist(address);
        return address;
    }

    @Override
    public Address editAddress(Address address) {
        em.merge(address);
        return address;
    }

    @Override
    public int deleteAddress(int addressId) {
        Query query = em.createQuery("delete from Address a where a.id = :id");
        query.setParameter("id", addressId);
        return query.executeUpdate();
    }

    @Override
    public long countNumberOfPeople() {
        return (long) em.createQuery("select count(p) FROM Person p").getSingleResult();
    }

}
