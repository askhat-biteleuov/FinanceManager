package com.epam.internal.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import java.io.Serializable;
import java.util.List;


public abstract class AbstractDao<T extends Serializable> {

    private Class<T> persistentClass;

    @Autowired
    private SessionFactory sessionFactory;

    public void setPersistentClass(Class<T> persistentClass) {
        this.persistentClass = persistentClass;
    }

    public T findById(long id){
        return (T)getCurrentSession().get(persistentClass, id);
    }

    @SuppressWarnings("unchecked")
    public List<T> findAll(){
        return getCurrentSession().createQuery( "from " + persistentClass.getName()).list();
    }

    public void create(T entity){
        getCurrentSession().persist(entity);
    }

    public void update(T entity){
        getCurrentSession().merge(entity);
    }

    public void delete(T entity){
        getCurrentSession().delete(entity);
    }

    public void deleteById(long id) {
        T entity = findById(id);
        delete(entity);
    }

    protected final Session getCurrentSession() {
        return sessionFactory.getCurrentSession();
    }

}
