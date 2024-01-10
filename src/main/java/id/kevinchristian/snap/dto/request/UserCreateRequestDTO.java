package id.kevinchristian.snap.dto.request;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import id.kevinchristian.snap.util.Constants;
import id.kevinchristian.snap.validator.annotation.UniqueEmail;
import id.kevinchristian.snap.validator.annotation.UniqueUsername;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record UserCreateRequestDTO(
        @NotBlank(message = Constants.ErrorMessage.Authentication.EMAIL_IS_REQUIRED) @Email(message = Constants.ErrorMessage.Authentication.EMAIL_IS_INVALID) @UniqueEmail String email,
        @NotBlank(message = Constants.ErrorMessage.Authentication.FIRST_NAME_IS_REQUIRED) String firstName,
        @NotBlank(message = Constants.ErrorMessage.Authentication.USERNAME_IS_REQUIRED) @UniqueUsername String username,
        @NotBlank(message = Constants.ErrorMessage.Authentication.PASSWORD_IS_REQUIRED) @Size(min = 8, message = Constants.ErrorMessage.Authentication.PASSWORD_IS_TOO_SHORT) @Pattern(regexp = "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9]).{1,}$", message = Constants.ErrorMessage.Authentication.PASSWORD_MUST_CONTAIN_LOWERCASE_UPPERCASE_AND_NUMBERS) String password,
        @NotBlank(message = Constants.ErrorMessage.Authentication.PASSWORD_CONFIRMATION_IS_REQUIRED) String confirmPassword) {
}
