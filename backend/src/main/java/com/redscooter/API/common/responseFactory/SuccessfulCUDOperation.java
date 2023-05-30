package com.redscooter.API.common.responseFactory;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;

public class SuccessfulCUDOperation {
    boolean NO_CONTENT = false;
    String resourceName;
    String primaryKeyColumnName;
    String primaryKeyValue;
    Operation operation;
    Object entity;

    public SuccessfulCUDOperation(Operation operation, String resourceName, String primaryKeyColumnName, String primaryKeyValue, Object entity) {
        this.operation = operation;
        this.resourceName = resourceName;
        this.primaryKeyColumnName = primaryKeyColumnName;
        this.primaryKeyValue = primaryKeyValue;
        this.entity = entity;
    }

    public SuccessfulCUDOperation(Operation operation, String resourceName, String primaryKeyColumnName, String primaryKeyValue) {
        this(operation, resourceName, primaryKeyColumnName, primaryKeyValue, null);
    }

    public SuccessfulCUDOperation(Operation operation) {
        this.operation = operation;
        this.NO_CONTENT = true;
    }

    ResponseEntity<Object> getResponseEntity() {

        HashMap<String, Object> res = new HashMap<>();
        if (NO_CONTENT)
            return new ResponseEntity<Object>(HttpStatus.NO_CONTENT);
        res.put("message", String.format("Successfully %s %s with %s: '%s'", operation + "D", resourceName, primaryKeyColumnName, primaryKeyValue));

        HashMap<String, Object> details = new HashMap<>();
        details.put("pk_name", primaryKeyColumnName);
        details.put("pk_value", primaryKeyValue);
        if (entity != null)
            details.put("entity", entity);
        res.put("details", details);
        return new ResponseEntity<Object>(res, HttpStatus.CREATED);
    }

    public enum Operation {
        CREATE,
        UPDATE,
        DELETE
    }
}
