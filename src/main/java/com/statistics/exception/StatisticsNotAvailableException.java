package com.statistics.exception;

public class StatisticsNotAvailableException extends RuntimeException {

    public StatisticsNotAvailableException( final String errorMessage ) {
        super( errorMessage );
    }
}
