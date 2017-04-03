package com.epam.internal.dao.test;

import com.epam.internal.data.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public  class GenericDao<T> {
    private Class<T> type;


    private SessionFactory sessionFactory;

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public GenericDao(Class<T> type) {
        this.type = type;
    }


    public void create(T newInstance) {
        getSessionFactory().openSession().save(newInstance);
    }

    public T read(Long id) {
        T instance = getSessionFactory().openSession().get(type, id);
        return instance;
    }

}
