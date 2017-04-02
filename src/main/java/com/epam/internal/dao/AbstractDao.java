package com.epam.internal.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;


public abstract class AbstractDao<PK extends Serializable, T> {

    private final Class<T> persistentClass;

    @Autowired
    private SessionFactory sessionFactory;

    @SuppressWarnings("unchecked")
    public AbstractDao() {
        this.persistentClass =(Class<T>) ((ParameterizedType)
                this.getClass().getGenericSuperclass()).getActualTypeArguments()[1];
    }

    protected Session getCurrentSession() {
        return sessionFactory.getCurrentSession();
    }

    public T getByKey(PK key){
        return getCurrentSession().get(persistentClass, key);
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


}
