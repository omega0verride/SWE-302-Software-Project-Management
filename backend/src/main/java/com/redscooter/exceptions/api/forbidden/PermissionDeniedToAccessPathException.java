package com.redscooter.exceptions.api.forbidden;

import com.redscooter.API.common.localFileStore.FileOperation;
import com.redscooter.exceptions.BaseException;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.nio.file.Path;

@Getter
@Setter
public class PermissionDeniedToAccessPathException extends BaseException {
    private Path targetPath_;
    public String targetPath;
    private Path rootAllowedPath_;
    public String rootAllowedPath;
    public FileOperation fileOperation;
    public String message;

    public PermissionDeniedToAccessPathException(Path targetPath_, String targetPath, Path rootAllowedPath_, String rootAllowedPath, FileOperation op) {
        super(HttpStatus.FORBIDDEN, "Cannot execute operation [" + op + "] on path [" + targetPath + "]. Current operation is limited to [" + rootAllowedPath + "]");
        this.rootAllowedPath_ = targetPath_;
        this.targetPath = targetPath;
        this.rootAllowedPath_ = rootAllowedPath_;
        this.rootAllowedPath = rootAllowedPath;
        this.fileOperation = op;
    }
}
