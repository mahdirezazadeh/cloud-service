package ir.urmia.cloudservice.controller.advice;

import ir.urmia.cloudservice.exception.UniqueAttributeAlreadyTakenException;
import org.apache.tomcat.util.http.fileupload.impl.SizeLimitExceededException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestResponseEntityExceptionHandler
        extends ResponseEntityExceptionHandler {

    @ExceptionHandler(UniqueAttributeAlreadyTakenException.class)
    protected ResponseEntity<Object> handleConflict(
            RuntimeException ex, WebRequest request) {

        String bodyOfResponse = ex.getMessage();
        return handleExceptionInternal(ex, bodyOfResponse,
                new HttpHeaders(), HttpStatus.CONFLICT, request);
    }

    @ExceptionHandler(SizeLimitExceededException.class)
    protected ResponseEntity<Object> handleSizeLimit(
            SizeLimitExceededException ex, WebRequest request) {

        String bodyOfResponse = "the request was rejected because its size (" +
                ex.getActualSize() +
                ") exceeds the configured maximum (" +
                ex.getPermittedSize() + ")";
        return handleExceptionInternal(ex, bodyOfResponse,
                new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler(MultipartException.class)
    protected ResponseEntity<Object> handleMultipart(
            Exception ex, WebRequest request) {

        String bodyOfResponse = "Current request is not a multipart request";
        return handleExceptionInternal(ex, bodyOfResponse,
                new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

}
