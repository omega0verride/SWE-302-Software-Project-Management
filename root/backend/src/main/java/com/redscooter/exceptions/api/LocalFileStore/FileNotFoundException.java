package com.redscooter.exceptions.api.LocalFileStore;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public class FileNotFoundException extends FileStoreException {
    public String path;

    public FileNotFoundException(String path) {
        super(HttpStatus.NOT_FOUND, "File with path: '" + path + "' does not exist!");
        this.path = path;
    }
}
