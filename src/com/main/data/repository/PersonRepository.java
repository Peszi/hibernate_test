package com.main.data.repository;

import com.main.data.model.Person;

import javax.persistence.EntityManager;
import java.util.List;

public class PersonRepository extends Repository<Person> {

    public PersonRepository(EntityManager entityManager) {
        super(entityManager, Person.class);
    }

    public List<Person> findByNameWithKeyword(String keyword) {
        return this.entityManager.createQuery("SELECT o FROM " + this.entityName + " o WHERE o.name LIKE :keyword")
                .setParameter("keyword", "%" + keyword + "%").getResultList();
    }

    public List<Person> findByName(String name) {
        return this.entityManager.createQuery("SELECT o FROM " + this.entityName + " o WHERE o.name = :name")
                .setParameter("name", name).getResultList();
    }
}
