package com.redscooter.exceptions;

import com.redscooter.exceptions.api.httpCore.HttpRequestMethodNotSupportedException;
import com.redscooter.exceptions.to_delete.ErrorResponse__;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.NotImplementedException;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.tomcat.util.http.fileupload.impl.SizeException;
import org.apache.tomcat.util.http.fileupload.impl.SizeLimitExceededException;
import org.restprocessors.exceptions.InvalidValueException;
import org.springframework.beans.ConversionNotSupportedException;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.*;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.ErrorResponseException;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.context.request.async.AsyncRequestTimeoutException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import jakarta.persistence.RollbackException;
import jakarta.validation.ConstraintViolationException;
import java.util.ArrayList;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@ControllerAdvice
public class GlobalResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    // captures all the custom exceptions that we throw and creates the default ErrorResponse from it
    @ExceptionHandler(BaseException.class)
    protected ResponseEntity<Object> customExceptionBaseHandler(BaseException ex) {
        return new ResponseEntity<>(new ErrorResponse(ex), ex.getHttpStatus());
    }

    @Override
    protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(org.springframework.web.HttpRequestMethodNotSupportedException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        String servletPath = null;
        try {
            servletPath = ((ServletWebRequest) request).getRequest().getServletPath();
        } catch (Exception ignored) {
        }
        return new HttpRequestMethodNotSupportedException(ex, servletPath).toResponseEntity();
    }

    @ExceptionHandler({TransactionSystemException.class})
    public ResponseEntity<Object> handleConstraintViolation(TransactionSystemException ex, WebRequest request) {

        if (ex.getCause() instanceof RollbackException) {
            RollbackException rollbackException = (RollbackException) ex.getCause();
            if (rollbackException.getCause() instanceof ConstraintViolationException) {
                return new com.redscooter.exceptions.to_refactor.ConstraintViolationException(HttpStatus.BAD_REQUEST, ex.getMessage()).toResponseEntity();
            }
        }
        return new UnknownException(ex).toResponseEntity(); // TODO
    }


    @Override
    protected ResponseEntity<Object> handleMissingPathVariable(MissingPathVariableException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        return super.handleMissingPathVariable(ex, headers, status, request);
    }


    //    @Override
//    protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
//        return "redirect:/your404page";
//    }

    @ExceptionHandler(InvalidValueException.class)
    protected ResponseEntity<ErrorResponse__> invalidParameterTypeExceptionHandler(InvalidValueException ex) {
        return new ResponseEntity<>(new ErrorResponse__(ex.getMessage()), BAD_REQUEST);
    }

    @Override
    @ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
    @ApiResponse(content = @Content)
    protected ResponseEntity<Object> handleHttpMediaTypeNotAcceptable(HttpMediaTypeNotAcceptableException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        return new BaseException(HttpStatus.UNSUPPORTED_MEDIA_TYPE, ex.getMessage()).toResponseEntity();
    }

    @Override
    @ResponseStatus(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
    @Schema(implementation = BaseException.class)
//    @ExceptionHandler(value = {InvalidMediaTypeException.class})
    protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(HttpMediaTypeNotSupportedException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        return new BaseException(HttpStatus.UNSUPPORTED_MEDIA_TYPE, ex.getMessage()).toResponseEntity();
    }




    @ExceptionHandler(value = NotImplementedException.class)
    protected ResponseEntity<ErrorResponse__> NotImplementedExceptionHandler(NotImplementedException ex) {
        return new ResponseEntity<>(new ErrorResponse__("This feature has not been implemented yet. Please check the detailed message for more info.", ex.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // TODO Override all methods with custom exceptions


    @Override
    protected ResponseEntity<Object> handleTypeMismatch(TypeMismatchException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        int causeIndex = ExceptionUtils.indexOfType(ex, BaseException.class);
        if (causeIndex != -1) {
            return ((BaseException) ExceptionUtils.getThrowables(ex)[causeIndex]).toResponseEntity();
        }
//        return new CustomTypeMismatchException();
        if (ex instanceof MethodArgumentTypeMismatchException ex_) {
            return new com.redscooter.exceptions.api.customExceptionHandlerBaseExceptions.MethodArgumentTypeMismatchException(ex_).toResponseEntity();
        }
        // TODO possible check for ConversionNotSupportedException

        return new com.redscooter.exceptions.api.customExceptionHandlerBaseExceptions.TypeMismatchException(ex).toResponseEntity();
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        if (ex.getFieldError() != null) {
            FieldError fieldError = ex.getFieldError();
            var x = fieldError.getField();
            var y = fieldError.getRejectedValue();
            y = fieldError.getDefaultMessage();
            y = fieldError.getCode();
            y = fieldError.getCodes();
            // TODO [2][validations] create FieldErrorException and handle it correctlly, including the fields aboce
        }
        ArrayList<String> detailsList = new ArrayList<>();
        for (ObjectError error : ex.getBindingResult().getAllErrors()) {
            detailsList.add(error.toString());
        }
        String message = ex.getBindingResult().getAllErrors().get(0).getDefaultMessage();
        return new ResponseEntity<>(new ErrorResponse__(message, detailsList), HttpStatus.BAD_REQUEST);
    }


//    @ExceptionHandler(MissingServletRequestParameterException.class)
//    protected ResponseEntity<Object> missingServletRequestParameterExceptionHandler(MissingServletRequestParameterException ex) {
//        ArrayList<String> detailsList = new ArrayList<>();
//        detailsList.add(ex.getMessage());
//        return new ResponseEntity<>(new ErrorResponse__(ex.getLocalizedMessage(), detailsList), HttpStatus.BAD_REQUEST);
//    }

    // TODO refactor


    @ExceptionHandler(MaxUploadSizeExceededException.class)
    protected ResponseEntity<Object> maxUploadSizeExceededExceptionHandler(MaxUploadSizeExceededException ex) {
        SizeException rootEx;
        boolean foundRootEx = true;
        try {
            rootEx = (SizeException) ex.getRootCause();
        } catch (Exception ex_) {
            rootEx = new SizeLimitExceededException("Maximum permitted size exceeded! Unspecified MaxUploadSizeExceededException.", -1, -1);
            foundRootEx = false;
        }

        ErrorResponse__ errorResponse = new ErrorResponse__("Maximum upload size exceeded!", rootEx.getMessage());
        errorResponse.putDetail("max-size", rootEx.getPermittedSize());
        errorResponse.putDetail("actual-size", rootEx.getActualSize());
        if (!foundRootEx) {
            errorResponse.addDetail(ex.getMessage());
            errorResponse.addDetail(rootEx.getMessage());
        }
        return new ResponseEntity<>(errorResponse, BAD_REQUEST);
    }

//    public ResponseEntity<Object> handleHttpRequestMethodNotSupported(org.springframework.web.HttpRequestMethodNotSupportedException ex, HttpServletRequest httpServletRequest){
//
//    }
//    public ResponseEntity<Object> handleException(Exception ex, HttpServletRequest httpServletRequest){
//        if (ex instanceof org.springframework.web.HttpRequestMethodNotSupportedException subEx) {
//            return handleHttpRequestMethodNotSupported(subEx, httpServletRequest);
//        }
//        else if (ex instanceof HttpMediaTypeNotSupportedException subEx) {
//            return handleHttpMediaTypeNotSupported(subEx, subEx.getHeaders(), subEx.getStatusCode(), request);
//        }
//        else if (ex instanceof HttpMediaTypeNotAcceptableException subEx) {
//            return handleHttpMediaTypeNotAcceptable(subEx, subEx.getHeaders(), subEx.getStatusCode(), request);
//        }
//        else if (ex instanceof MissingPathVariableException subEx) {
//            return handleMissingPathVariable(subEx, subEx.getHeaders(), subEx.getStatusCode(), request);
//        }
//        else if (ex instanceof MissingServletRequestParameterException subEx) {
//            return handleMissingServletRequestParameter(subEx, subEx.getHeaders(), subEx.getStatusCode(), request);
//        }
//        else if (ex instanceof MissingServletRequestPartException subEx) {
//            return handleMissingServletRequestPart(subEx, subEx.getHeaders(), subEx.getStatusCode(), request);
//        }
//        else if (ex instanceof ServletRequestBindingException subEx) {
//            return handleServletRequestBindingException(subEx, subEx.getHeaders(), subEx.getStatusCode(), request);
//        }
//        else if (ex instanceof MethodArgumentNotValidException subEx) {
//            return handleMethodArgumentNotValid(subEx, subEx.getHeaders(), subEx.getStatusCode(), request);
//        }
//        else if (ex instanceof NoHandlerFoundException subEx) {
//            return handleNoHandlerFoundException(subEx, subEx.getHeaders(), subEx.getStatusCode(), request);
//        }
//        else if (ex instanceof AsyncRequestTimeoutException subEx) {
//            return handleAsyncRequestTimeoutException(subEx, subEx.getHeaders(), subEx.getStatusCode(), request);
//        }
//        else if (ex instanceof ErrorResponseException subEx) {
//            return handleErrorResponseException(subEx, subEx.getHeaders(), subEx.getStatusCode(), request);
//        }
//
//        // Lower level exceptions, and exceptions used symmetrically on client and server
//
//        HttpHeaders headers = new HttpHeaders();
//        if (ex instanceof ConversionNotSupportedException theEx) {
//            return handleConversionNotSupported(theEx, headers, HttpStatus.INTERNAL_SERVER_ERROR, request);
//        }
//        else if (ex instanceof TypeMismatchException theEx) {
//            return handleTypeMismatch(theEx, headers, HttpStatus.BAD_REQUEST, request);
//        }
//        else if (ex instanceof HttpMessageNotReadableException theEx) {
//            return handleHttpMessageNotReadable(theEx, headers, HttpStatus.BAD_REQUEST, request);
//        }
//        else if (ex instanceof HttpMessageNotWritableException theEx) {
//            return handleHttpMessageNotWritable(theEx, headers, HttpStatus.INTERNAL_SERVER_ERROR, request);
//        }
//        else if (ex instanceof BindException theEx) {
//            return handleBindException(theEx, headers, HttpStatus.BAD_REQUEST, request);
//        }
//        else {
//            // Unknown exception, typically a wrapper with a common MVC exception as cause
//            // (since @ExceptionHandler type declarations also match nested causes):
//            // We only deal with top-level MVC exceptions here, so let's rethrow the given
//            // exception for further processing through the HandlerExceptionResolver chain.
//            throw ex;
//        }
//    }

}