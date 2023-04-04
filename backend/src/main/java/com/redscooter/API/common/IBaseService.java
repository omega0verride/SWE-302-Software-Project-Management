package com.redscooter.API.common;

import com.redscooter.exceptions.api.ResourceNotFoundException;

public interface IBaseService<T> {
    boolean existsById(Long id);

    boolean existsById(Long id, boolean throwNotFoundEx);

    T getById(Long id);

    T save(T entity);

    //    T create(ICreateDTO createObject);
//
//    T update(IUpdateDTO updateObject);
    void delete(Long id);

    void delete(Long id, boolean throwNotFoundEx);

    ResourceNotFoundException buildResourceNotFoundException(String field, Object value);
}
