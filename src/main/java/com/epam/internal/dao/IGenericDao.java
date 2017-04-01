package com.epam.internal.dao;

import java.io.Serializable;

public interface IGenericDao<T extends Serializable> {

    T findById(final long id);

    void create(final T entity);

    void delete(final T entity);

    void deleteById(final long id);

}
