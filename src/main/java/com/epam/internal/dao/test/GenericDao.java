package com.epam.internal.dao.test;

import com.epam.internal.data.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;

public abstract class GenericDao<T> {
    private Class<T> type;
    private Session currentSession;
    private Transaction currentTransaction;

    public GenericDao(Class<T> type) {
        this.type = type;
    }

    public static SessionFactory getSessionFactory() {
        return HibernateUtil.getSessionFactory();
    }

    public void create(T newInstance) {
        openCurrentSessionWithTransaction();
        getCurrentSession().save(newInstance);
        closeCurrentSessionWithTransaction();
    }

    public T read(Long id) {
        openCurrentSession();
        T instance = getCurrentSession().get(type, id);
        closeCurrentSession();
        return instance;
    }

    public void update(T transientObject) {
        openCurrentSessionWithTransaction();
        getCurrentSession().update(transientObject);
        closeCurrentSessionWithTransaction();
    }

    public void delete(T persistentObject) {
        openCurrentSessionWithTransaction();
        getCurrentSession().delete(persistentObject);
        closeCurrentSessionWithTransaction();
    }

    public List<T> getAll() {
        openCurrentSessionWithTransaction();
        List<T> instances = currentSession.createQuery("from " + type.getName()).list();
        closeCurrentSessionWithTransaction();
        return instances;
    }

    public Session getCurrentSession() {
        return currentSession;
    }

    public Transaction getCurrentTransaction() {
        return currentTransaction;
    }

    public Session openCurrentSession() {
        currentSession = getSessionFactory().openSession();
        return currentSession;
    }

    public Session openCurrentSessionWithTransaction() {
        currentSession = getSessionFactory().openSession();
        currentTransaction = currentSession.beginTransaction();
        return currentSession;
    }

    public void closeCurrentSession() {
        currentSession.close();
    }

    public void closeCurrentSessionWithTransaction() {
        currentTransaction.commit();
        currentSession.close();
    }

    protected Class<T> getType() {
        return type;
    }
}
