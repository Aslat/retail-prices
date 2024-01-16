package com.example.Prices.infrastructure.rest.exceptionhandler;

import com.example.Prices.domain.exceptions.PriceNotFoundException;
import org.openapitools.model.ExceptionErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@ControllerAdvice
public class ExceptionHandlerAdvice {

    @ExceptionHandler(PriceNotFoundException.class)
    public ResponseEntity<ExceptionErrorResponse> handlePriceNotFoundException(PriceNotFoundException e) {
        ExceptionErrorResponse exceptionErrorResponse = new ExceptionErrorResponse();
        exceptionErrorResponse.setType(e.getHttpStatus().toString());
        exceptionErrorResponse.setMessage(e.getMessage());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exceptionErrorResponse);
    }

    @ExceptionHandler({MethodArgumentTypeMismatchException.class, MissingServletRequestParameterException.class})
    public ResponseEntity<ExceptionErrorResponse> handlePriceBadRequestException(Exception e) {
        ExceptionErrorResponse exceptionErrorResponse = new ExceptionErrorResponse();
        exceptionErrorResponse.setType(HttpStatus.BAD_REQUEST.toString());
        exceptionErrorResponse.setMessage(e.getMessage());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exceptionErrorResponse);
    }
}
