package com.redscooter.exceptions.api.LocalFileStore;

import com.redscooter.exceptions.BaseException;
import org.springframework.http.HttpStatus;

// this class is only used to have a high level class that includes all FileStore Exceptions
public class FileStoreException extends BaseException {
    protected FileStoreException(HttpStatus httpStatus, String message) {
        super(httpStatus, message);
    }
}
