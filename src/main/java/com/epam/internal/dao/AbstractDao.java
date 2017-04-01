package com.epam.internal.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import java.io.Serializable;


public abstract class AbstractDao<T extends Serializable> {

    private final Class<T> persistentClass;

    @Autowired
    private SessionFactory sessionFactory;

    AbstractDao(Class<T> persistentClass) {
        this.persistentClass = persistentClass;
    }

    public T findById(long id){
        return (T)getCurrentSession().get(persistentClass, id);
    }

    public void create(T entity){
        getCurrentSession().persist(entity);
    }

    public void delete(T entity){
        getCurrentSession().delete(entity);
    }
    public void deleteById(long id) {
        T entity = findById(id);
        delete( entity );
    }

    protected final Session getCurrentSession() {
        return sessionFactory.getCurrentSession();
    }
}
