package com.redscooter.exceptions.api.httpCore;

import com.redscooter.exceptions.BaseException;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;

import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
public class HttpRequestMethodNotSupportedException extends BaseException {
    public String path;
    public String method;
    private transient HttpMethod method_;
    public Set<String> supportedHTTPMethods;
    private transient Set<HttpMethod> supportedHTTPMethods_;

    public HttpRequestMethodNotSupportedException(HttpMethod method, String path, Set<HttpMethod> supportedHTTPMethods) {
        super(HttpStatus.METHOD_NOT_ALLOWED, "Request method '" + method + "' not supported");
        setMethod_(method);
        setSupportedHTTPMethods_(supportedHTTPMethods);
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

    public void setMethod_(HttpMethod method_) {
        this.method_ = method_;
        this.method = method_.name();
    }

    public void setSupportedHTTPMethods_(Set<HttpMethod> supportedHTTPMethods_) {
        this.supportedHTTPMethods_ = supportedHTTPMethods_;
        this.supportedHTTPMethods = supportedHTTPMethods_.stream().map(HttpMethod::toString).collect(Collectors.toSet());
    }
}
