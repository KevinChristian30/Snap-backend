package id.kevinchristian.snap.validator.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import id.kevinchristian.snap.util.Constants;
import id.kevinchristian.snap.validator.UniqueUsernameValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Constraint(validatedBy = UniqueUsernameValidator.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface UniqueUsername {
    String message() default Constants.ErrorMessage.Authentication.USERNAME_IS_TAKEN;

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
