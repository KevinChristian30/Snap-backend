package id.kevinchristian.snap.dto.response;

import java.util.List;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record UserResponseDTO(
    String email,
    List<String> roles,
    String username,
    Boolean isEmailVerified
) {
    
}
