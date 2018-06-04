package com.main.data;

import com.main.Main;
import com.main.data.model.Person;
import com.main.data.repository.PersonRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;

public class Database {

    private final String[] NAMES = {"John", "Bob", "Bobby"};

    static Logger LOGGER = LoggerFactory.getLogger(Main.class);

    private EntityManager entityManager;
    private PersonRepository personRepository;

    public Database() {
        this.entityManager = Persistence.createEntityManagerFactory("test").createEntityManager();
        this.personRepository = new PersonRepository(this.entityManager);

        System.out.println("Delete " + this.personRepository.deleteAll() + " persons");

        for (String name : this.NAMES)
            this.personRepository.save(new Person(name));

        this.personRepository.findAll().forEach(System.out::println);

        System.out.println();

        this.personRepository.findByNameWithKeyword("Bob").forEach(System.out::println);
    }
}
