package com.statistics.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.Instant;
import java.util.Date;

public class TimeStampValidator implements ConstraintValidator<ValidTimeStamp, Date> {
    private static final long SECONDS_TO_SUBTRACT = 60L;

    @Override
    public void initialize( final ValidTimeStamp constraintAnnotation ) {
    }

    @Override
    public boolean isValid( final Date timestamp, final ConstraintValidatorContext context ) {
        final Date timeStampForLastMinute = Date.from( Instant.now().minusSeconds( SECONDS_TO_SUBTRACT ) );
        return timeStampForLastMinute.before( timestamp );
    }
}
