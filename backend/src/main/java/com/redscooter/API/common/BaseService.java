package com.redscooter.API.common;


import com.redscooter.exceptions.api.ResourceAlreadyExistsException;
import com.redscooter.exceptions.api.ResourceNotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public abstract class BaseService<T> implements IBaseService<T> {
    JpaRepository<T, Long> repository;
    String entityName;

    public BaseService(JpaRepository<T, Long> repository, String entityName) {
        this.repository = repository;
        this.entityName = entityName;
    }

    public boolean existsById(Long id) {
        return existsById(id, false);
    }

    public boolean existsById(Long id, boolean throwNotFoundEx) {
        if (!repository.existsById(id)) {
            if (throwNotFoundEx)
                throw buildResourceNotFoundException("Id", id);
            return false;
        }
        return true;
    }

    public T getById(Long id, boolean throwNotFoundException) {
        Optional<T> entity = repository.findById(id);
        if (entity.isPresent())
            return entity.get();
        if (throwNotFoundException)
            throw buildResourceNotFoundException("Id", id);
        return null;
    }
    public T getById(Long id) {
        return getById(id, true);
    }

    @Override
    public T save(T entity) {
        return repository.save(entity);
    }

    @Override
    public void delete(Long id) {
        delete(id, true);
    }

    @Override
    public void delete(Long id, boolean throwNotFoundEx) {
        if (existsById(id, throwNotFoundEx))
            repository.deleteById(id);
    }

    @Override
    public ResourceNotFoundException buildResourceNotFoundException(String field, Object value) {
        return new ResourceNotFoundException(entityName, field, value);
    }

    @Override
    public ResourceAlreadyExistsException buildResourceAlreadyExistsException(String field, Object value) {
        return new ResourceAlreadyExistsException(entityName, field, value);
    }


}
