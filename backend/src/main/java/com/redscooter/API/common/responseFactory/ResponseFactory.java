package com.redscooter.API.common.responseFactory;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.function.Function;
import java.util.stream.Collectors;

public class ResponseFactory {


    public static ResponseEntity<Object> buildResourceUpdatedSuccessfullyResponse(String resourceName, String primaryKeyColumnName, String primaryKeyValue, Object updatedValue) {
        return new SuccessfulCUDOperation(SuccessfulCUDOperation.Operation.UPDATE, resourceName, primaryKeyColumnName, primaryKeyValue, updatedValue).getResponseEntity();
    }

    public static ResponseEntity<Object> buildResourceUpdatedSuccessfullyResponse(String resourceName, String primaryKeyColumnName, Long primaryKeyValue, Object updatedValue) {
        return buildResourceUpdatedSuccessfullyResponse(resourceName, primaryKeyColumnName, primaryKeyValue.toString(), updatedValue);
    }

    public static ResponseEntity<Object> buildResourceUpdatedSuccessfullyResponse() {
        return new SuccessfulCUDOperation(SuccessfulCUDOperation.Operation.UPDATE).getResponseEntity();
    }

    public static ResponseEntity<Object> buildResourceDeletedSuccessfullyResponse(String resourceName, String primaryKeyColumnName, String primaryKeyValue) {
        return new SuccessfulCUDOperation(SuccessfulCUDOperation.Operation.DELETE, resourceName, primaryKeyColumnName, primaryKeyValue).getResponseEntity();
    }

    public static ResponseEntity<Object> buildResourceDeletedSuccessfullyResponse(String resourceName, String primaryKeyColumnName, Long primaryKeyValue) {
        return buildResourceDeletedSuccessfullyResponse(resourceName, primaryKeyColumnName, primaryKeyValue.toString());
    }

    public static ResponseEntity<Object> buildResourceDeletedSuccessfullyResponse() {
        return new SuccessfulCUDOperation(SuccessfulCUDOperation.Operation.DELETE).getResponseEntity();
    }

    public static ResponseEntity<Object> buildResourceCreatedSuccessfullyResponse(String resourceName, String primaryKeyColumnName, String primaryKeyValue) {
        return new SuccessfulCUDOperation(SuccessfulCUDOperation.Operation.CREATE, resourceName, primaryKeyColumnName, primaryKeyValue).getResponseEntity();
    }

    public static ResponseEntity<Object> buildResourceCreatedSuccessfullyResponse(String resourceName, String primaryKeyColumnName, Long primaryKeyValue) {
        return buildResourceCreatedSuccessfullyResponse(resourceName, primaryKeyColumnName, primaryKeyValue.toString());
    }

    public static ResponseEntity<Object> buildResourceCreatedSuccessfullyResponse() {
        return new SuccessfulCUDOperation(SuccessfulCUDOperation.Operation.CREATE).getResponseEntity();
    }

    public static <T> ResponseEntity<PageResponse<T>> buildPageResponse(Page<T> page) {
        return new ResponseEntity<>(new PageResponse<>(page), HttpStatus.OK);
    }

    public static <T, R> ResponseEntity<PageResponse<R>> buildPageResponse(Page<T> page, Function<? super T, ? extends R> mapper) {
        return new ResponseEntity<>(new PageResponse<>(page.getNumber(), page.getTotalElements(), page.getTotalPages(), page.getContent().stream().map(mapper).collect(Collectors.toList())), HttpStatus.OK);
    }

    public static <T> ResponseEntity<Object> buildGenericSuccessfulResponse(String message) {
        return new ResponseEntity<>(new GenericSuccessfulResponse<T>(message), HttpStatus.OK);
    }

    public static <T> ResponseEntity<Object> buildGenericSuccessfulResponse(String message, T value) {
        return new ResponseEntity<>(new GenericSuccessfulResponse<T>(message, value), HttpStatus.OK);
    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    static class GenericSuccessfulResponseBase {
        String message;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    static class GenericSuccessfulResponse<T> extends GenericSuccessfulResponseBase {
        T value;

        public GenericSuccessfulResponse(String message) {
            super(message);
        }

        public GenericSuccessfulResponse(String message, T value) {
            super(message);
            this.value=value;
        }
    }
}
