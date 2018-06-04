package com.main.data.repository;

import javax.persistence.EntityManager;
import java.util.List;

public abstract class Repository<T> {

    EntityManager entityManager;

    private final Class<T> entityClass;
    final String entityName;

    Repository(EntityManager entityManager, Class<T> entityClass) {
        this(entityManager, entityClass, entityClass.getSimpleName());
    }

    Repository(EntityManager entityManager, Class<T> entityClass, String entityName) {
        this.entityManager = entityManager;
        this.entityClass = entityClass;
        this.entityName = entityName;
    }

    public void save(T object) {
        this.entityManager.getTransaction().begin();
        this.entityManager.persist(object);
        this.entityManager.getTransaction().commit();
    }

    public void delete(T object) {
        this.entityManager.getTransaction().begin();
        this.entityManager.remove(object);
        this.entityManager.getTransaction().commit();
    }

    public int deleteAll() {
        this.entityManager.getTransaction().begin();
        final int affectedRows = this.entityManager.createQuery("DELETE FROM " + this.entityName).executeUpdate();
        this.entityManager.getTransaction().commit();
        return affectedRows;
    }

    public T getOne(Object object) {
        return this.entityManager.find(this.entityClass, object);
    }

    public List<T> findAll() {
        return this.entityManager.createQuery(this.getSelectQuery()).getResultList();
    }

    public List<T> findAll(int limit) {
        return this.entityManager.createQuery(this.getSelectQuery()).setMaxResults(limit).getResultList();
    }

    String getSelectQuery() {
        return "FROM " + this.entityName + " o"; // SELECT o
    }
}
