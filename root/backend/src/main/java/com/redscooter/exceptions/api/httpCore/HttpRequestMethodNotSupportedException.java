package com.redscooter.exceptions.api.httpCore;

import com.redscooter.exceptions.BaseException;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;

import java.util.Set;

@Getter
@Setter
public class HttpRequestMethodNotSupportedException extends BaseException {
    public String path;
    public HttpMethod method;
    public Set<HttpMethod> supportedHTTPMethods;

    public HttpRequestMethodNotSupportedException(HttpMethod method, String path, Set<HttpMethod> supportedHTTPMethods) {
        super(HttpStatus.METHOD_NOT_ALLOWED, "Request method '" + method + "' not supported");
        setMethod(method);
        setSupportedHTTPMethods(supportedHTTPMethods);
        setPath(path);
    }

    public HttpRequestMethodNotSupportedException(HttpMethod method, Set<HttpMethod> supportedHTTPMethods) {
        this(method, null, supportedHTTPMethods);
    }

    public HttpRequestMethodNotSupportedException(HttpMethod method, String path) {
        this(method, path, null);
    }

    public HttpRequestMethodNotSupportedException(HttpMethod method) {
        this(method, null, null);
    }

    public HttpRequestMethodNotSupportedException(org.springframework.web.HttpRequestMethodNotSupportedException ex, String path) {
        this(HttpMethod.valueOf(ex.getMethod()), path, ex.getSupportedHttpMethods());
        setRootException(ex);
    }

    public HttpRequestMethodNotSupportedException(org.springframework.web.HttpRequestMethodNotSupportedException ex) {
        this(ex, null);
    }
}
