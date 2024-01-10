package id.kevinchristian.snap.validator.annotation;

import id.kevinchristian.snap.util.Constants;
import id.kevinchristian.snap.validator.UniqueEmailValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = UniqueEmailValidator.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface UniqueEmail {
    String message() default Constants.ErrorMessage.Authentication.EMAIL_IS_TAKEN;

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
