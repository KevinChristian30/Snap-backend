package id.kevinchristian.snap.dto.response;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record UserDetailResponseDTO(
    String id,
    String email,
    String firstName,
    String lastName,
    String username,
    String bio
) {
    
}
