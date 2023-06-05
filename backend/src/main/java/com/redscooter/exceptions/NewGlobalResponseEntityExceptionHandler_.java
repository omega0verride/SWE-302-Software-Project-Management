//package com.redscooter.exceptions;
//
//import io.swagger.v3.oas.annotations.responses.ApiResponses;
//import org.springframework.beans.ConversionNotSupportedException;
//import org.springframework.beans.TypeMismatchException;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.HttpStatus;
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
//
//@ControllerAdvice
//public class NewGlobalResponseEntityExceptionHandler_ {
//
//    //    Handle all exceptions raised within Spring MVC handling of the request.
////            Params:
////    ex – the exception to handle requ
//    @ApiResponses(HttpStatus.INTERNAL_SERVER_ERROR)
//    @ExceptionHandler(Exception.class)
//    private ResponseEntity<ErrorResponse> handleException(Exception ex, WebRequest request) {
//        return null;
//    }
//
//    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
//    @ExceptionHandler(UnknownException.class)
//    private ResponseEntity<ErrorResponse> handleUnknownException(UnknownException ex, WebRequest request) {
//        return null;
//    }
//
//    private ResponseEntity<ErrorResponse> handleBindException(BindException theEx, HttpHeaders headers, HttpStatus badRequest, WebRequest request) {
//        return null;
//    }
//
//    private ResponseEntity<ErrorResponse> handleHttpMessageNotWritable(HttpMessageNotWritableException theEx, HttpHeaders headers, HttpStatus internalServerError, WebRequest request) {
//        return null;
//    }
//
//    private ResponseEntity<ErrorResponse> handleHttpMessageNotReadable(HttpMessageNotReadableException theEx, HttpHeaders headers, HttpStatus badRequest, WebRequest request) {
//        return null;
//    }
//
//    private ResponseEntity<ErrorResponse> handleTypeMismatch(TypeMismatchException theEx, HttpHeaders headers, HttpStatus badRequest, WebRequest request) {
//        return null;
//    }
//
//    private ResponseEntity<ErrorResponse> handleConversionNotSupported(ConversionNotSupportedException theEx, HttpHeaders headers, HttpStatus internalServerError, WebRequest request) {
//        return null;
//    }
//
//    private ResponseEntity<ErrorResponse> handleErrorResponseException(ErrorResponseException subEx, WebRequest request) {
//        return null;
//    }
//
//    private ResponseEntity<ErrorResponse> handleAsyncRequestTimeoutException(AsyncRequestTimeoutException subEx, WebRequest request) {
//        return null;
//    }
//
//    private ResponseEntity<ErrorResponse> handleNoHandlerFoundException(NoHandlerFoundException subEx, WebRequest request) {
//        return null;
//    }
//
//    private ResponseEntity<ErrorResponse> handleMethodArgumentNotValid(MethodArgumentNotValidException subEx, WebRequest request) {
//        return null;
//    }
//
//    private ResponseEntity<ErrorResponse> handleServletRequestBindingException(ServletRequestBindingException subEx, WebRequest request) {
//        return null;
//    }
//
//    private ResponseEntity<ErrorResponse> handleMissingServletRequestPart(MissingServletRequestPartException subEx, WebRequest request) {
//        return null;
//    }
//
//    private ResponseEntity<ErrorResponse> handleMissingServletRequestParameter(MissingServletRequestParameterException subEx, WebRequest request) {
//        return null;
//    }
//
//    private ResponseEntity<ErrorResponse> handleMissingPathVariable(MissingPathVariableException subEx, WebRequest request) {
//        return null;
//    }
//
//    private ResponseEntity<ErrorResponse> handleHttpMediaTypeNotAcceptable(HttpMediaTypeNotAcceptableException subEx, WebRequest request) {
//        return null;
//    }
//
//    @ResponseStatus(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
//    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
//    private ResponseEntity<ErrorResponse> handleHttpMediaTypeNotSupported(HttpMediaTypeNotSupportedException subEx, WebRequest request) {
//        return null;
//    }
//
//    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
//    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
//    private ResponseEntity<ErrorResponse> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException subEx, WebRequest request) {
//        return null;
//    }
//}