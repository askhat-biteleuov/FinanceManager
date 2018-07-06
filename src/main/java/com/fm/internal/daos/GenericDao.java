package com.fm.internal.daos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

public class GenericDao<T> {

    private Class<T> type;

    @Autowired
    private EntityManager entityManager;

    public GenericDao(Class<T> type) {
        this.type = type;
    }

    public EntityManager getEntityManager() {
        return entityManager;
    }

    public void setSessionFactory(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Transactional
    public void add(T entity) {
        entityManager.merge(entity);
//        sessionFactory.getCurrentSession().save(entity);
    }

    @Transactional(readOnly = true)
    public T getById(long id) {
//        return sessionFactory.getCurrentSession().get(type, id);
        return (T) entityManager.find(type.getClass(), id);
    }

    @Transactional
    public void update(T entity) {
        entityManager.merge(entity);
    }

    @Transactional
    public void saveOrUpdate(T entity) {
        entityManager.merge(entity);
    }

    @Transactional
    public void delete(T entity) {
        entityManager.remove(entity);
    }

    @Transactional(readOnly = true)
    @SuppressWarnings("unchecked")
    public List<T> getAll() {
//        return (List<T>) sessionFactory.getCurrentSession().createQuery("from " + type.getName()).list();
        return entityManager.createNativeQuery("from " + type.getName()).getResultList();
    }

}
