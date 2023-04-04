package com.redscooter.exceptions.api.LocalFileStore;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public class InvalidUploadFileType extends FileStoreException {
    public String file;
    public String expectedType;
    public String providedType;

    public InvalidUploadFileType(String file, String expectedType, String providedType) {
        super(HttpStatus.BAD_REQUEST, String.format("Invalid file type! Expected: %s, Found: %s", expectedType, providedType));
        this.file = file;
        this.expectedType = expectedType;
        this.providedType = providedType;
    }
}
