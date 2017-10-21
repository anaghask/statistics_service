package com.statistics.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;

@Documented
@Target( { METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER } )
@Retention( RetentionPolicy.RUNTIME )
@Constraint( validatedBy = TimeStampValidator.class )
public @interface ValidTimeStamp {
    String message() default "{org.hibernate.validator.Date.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
