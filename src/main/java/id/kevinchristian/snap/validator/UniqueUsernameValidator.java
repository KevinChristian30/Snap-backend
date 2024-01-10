package id.kevinchristian.snap.validator;

import id.kevinchristian.snap.repository.UserRepository;
import id.kevinchristian.snap.validator.annotation.UniqueUsername;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class UniqueUsernameValidator implements ConstraintValidator<UniqueUsername, String> {
    private final UserRepository userRepository;

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return userRepository.findByUsername(value).isEmpty();
    }
}
