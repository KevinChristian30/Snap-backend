package id.kevinchristian.snap.dto.response;

import java.util.List;

public record UserResponseDTO(
    String email,
    List<String> roles,
    String username,
    Boolean isEmailVerified
) {
    
}
