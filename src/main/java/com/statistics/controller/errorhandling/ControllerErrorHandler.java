package com.statistics.controller.errorhandling;

import com.statistics.exception.StatisticsNotAvailableException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ControllerErrorHandler {

    @ExceptionHandler( StatisticsNotAvailableException.class )
    public ResponseEntity handleStatisticsNotAvailableException( final StatisticsNotAvailableException exception ) {
        return new ResponseEntity<>( exception.getLocalizedMessage(), HttpStatus.NO_CONTENT );
    }

    @ExceptionHandler( RuntimeException.class )
    public ResponseEntity handleRunTimeException( final RuntimeException exception ) {
        return new ResponseEntity<>( exception.getLocalizedMessage(), HttpStatus.INTERNAL_SERVER_ERROR );
    }

    @ExceptionHandler( MethodArgumentNotValidException.class )
    public ResponseEntity handleInvalidInput( MethodArgumentNotValidException exception ) {
        return new ResponseEntity<>( exception.getLocalizedMessage(), HttpStatus.NO_CONTENT );
    }

}