//package com.redscooter.exceptions;
//
//import io.swagger.v3.oas.annotations.responses.ApiResponses;
//import org.springframework.beans.ConversionNotSupportedException;
//import org.springframework.beans.TypeMismatchException;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.HttpStatusCode;
//import org.springframework.http.ResponseEntity;
//import org.springframework.http.converter.HttpMessageNotReadableException;
//import org.springframework.http.converter.HttpMessageNotWritableException;
//import org.springframework.validation.BindException;
//import org.springframework.web.ErrorResponseException;
//import org.springframework.web.HttpMediaTypeNotAcceptableException;
//import org.springframework.web.HttpMediaTypeNotSupportedException;
//import org.springframework.web.HttpRequestMethodNotSupportedException;
//import org.springframework.web.bind.MethodArgumentNotValidException;
//import org.springframework.web.bind.MissingPathVariableException;
//import org.springframework.web.bind.MissingServletRequestParameterException;
//import org.springframework.web.bind.ServletRequestBindingException;
//import org.springframework.web.bind.annotation.ControllerAdvice;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//import org.springframework.web.bind.annotation.ResponseStatus;
//import org.springframework.web.context.request.WebRequest;
//import org.springframework.web.context.request.async.AsyncRequestTimeoutException;
//import org.springframework.web.multipart.support.MissingServletRequestPartException;
//import org.springframework.web.servlet.NoHandlerFoundException;
//import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
//
//@ControllerAdvice
//public class NewGlobalResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {
//
//
//
////    @ResponseStatus
////    @ExceptionHandler(UnknownException.class)
////    private ResponseEntity<ErrorResponse> handleUnknownException(UnknownException ex, WebRequest request) {
////        return null;
////    }
////
////    @ResponseStatus
////    @ExceptionHandler(BaseException.class)
////    private ResponseEntity<ErrorResponse> handleUnknownException(BaseException ex, WebRequest request) {
////        return null;
////    }
//
//
//    @Override
//    protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
//        return super.handleHttpRequestMethodNotSupported(ex, headers, status, request);
//    }
//
//    @Override
//    protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(HttpMediaTypeNotSupportedException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
//        return super.handleHttpMediaTypeNotSupported(ex, headers, status, request);
//    }
//
//    @Override
//    protected ResponseEntity<Object> handleHttpMediaTypeNotAcceptable(HttpMediaTypeNotAcceptableException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
//        return super.handleHttpMediaTypeNotAcceptable(ex, headers, status, request);
//    }
//
//    @Override
//    protected ResponseEntity<Object> handleMissingPathVariable(MissingPathVariableException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
//        return super.handleMissingPathVariable(ex, headers, status, request);
//    }
//
//    @Override
//    protected ResponseEntity<Object> handleMissingServletRequestParameter(MissingServletRequestParameterException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
//        return super.handleMissingServletRequestParameter(ex, headers, status, request);
//    }
//
//    @Override
//    protected ResponseEntity<Object> handleMissingServletRequestPart(MissingServletRequestPartException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
//        return super.handleMissingServletRequestPart(ex, headers, status, request);
//    }
//
//    @Override
//    protected ResponseEntity<Object> handleServletRequestBindingException(ServletRequestBindingException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
//        return super.handleServletRequestBindingException(ex, headers, status, request);
//    }
//
//    @Override
//    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
//        return super.handleMethodArgumentNotValid(ex, headers, status, request);
//    }
//
//    @Override
//    protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
//        return super.handleNoHandlerFoundException(ex, headers, status, request);
//    }
//
//    @Override
//    protected ResponseEntity<Object> handleAsyncRequestTimeoutException(AsyncRequestTimeoutException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
//        return super.handleAsyncRequestTimeoutException(ex, headers, status, request);
//    }
//
//    @Override
//    protected ResponseEntity<Object> handleErrorResponseException(ErrorResponseException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
//        return super.handleErrorResponseException(ex, headers, status, request);
//    }
//
//    @Override
//    protected ResponseEntity<Object> handleConversionNotSupported(ConversionNotSupportedException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
//        return super.handleConversionNotSupported(ex, headers, status, request);
//    }
//
//    @Override
//    protected ResponseEntity<Object> handleTypeMismatch(TypeMismatchException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
//        return super.handleTypeMismatch(ex, headers, status, request);
//    }
//
//    @Override
//    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
//        return super.handleHttpMessageNotReadable(ex, headers, status, request);
//    }
//
//    @Override
//    protected ResponseEntity<Object> handleHttpMessageNotWritable(HttpMessageNotWritableException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
//        return super.handleHttpMessageNotWritable(ex, headers, status, request);
//    }
//
//    @Override
//    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers, HttpStatusCode statusCode, WebRequest request) {
//        throw new UnknownException(ex);
////        return super.handleExceptionInternal(ex, body, headers, statusCode, request);
//    }
//}