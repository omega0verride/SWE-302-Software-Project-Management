package com.redscooter.exceptions.api.LocalFileStore;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.nio.file.Path;

@Getter
@Setter
public class UnableToCreateDirectory extends FileStoreException {
    public String path;

    public UnableToCreateDirectory(String path) {
        super(HttpStatus.INTERNAL_SERVER_ERROR, "Unable to create directory path: '" + path + "'");
        this.path = path;
    }

    public UnableToCreateDirectory(Path path) {
        this(path.toString());
    }

    public UnableToCreateDirectory(String path, Exception rootException) {
        this(path);
        setRootException(rootException);
    }

}
