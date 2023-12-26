package id.kevinchristian.snap.web;

import id.kevinchristian.snap.dto.request.UserCreateRequestDTO;
import id.kevinchristian.snap.dto.response.UserResponseDTO;
import id.kevinchristian.snap.service.AuthService;
import id.kevinchristian.snap.util.Constants;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;



@RestController
@AllArgsConstructor
public class AuthResource {
    private final AuthService authService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(Constants.SIGN_UP_URL)
    public ResponseEntity<Void> signUp(@RequestBody @Valid UserCreateRequestDTO dto) {
        authService.signUp(dto);
        return ResponseEntity.created(URI.create(Constants.SIGN_UP_URL)).build();
    }

    @GetMapping("/v1/auth/me")
    public ResponseEntity<UserResponseDTO> getCurrentUser() {
        return ResponseEntity.ok(authService.findCurrentUserDetails());
    }

    @GetMapping("/v1/auth/confirm-email")
    public ResponseEntity<Void> getMethodName() {
        authService.sendEmailConfirmation();
        return ResponseEntity.ok().build();
    }
    
}
