package id.kevinchristian.snap.web;

import org.springframework.web.bind.annotation.RestController;

import id.kevinchristian.snap.dto.response.UserDetailResponseDTO;
import id.kevinchristian.snap.service.UserService;
import lombok.AllArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;


@RestController
@AllArgsConstructor
public class UserResource {
    private final UserService userService;

    @GetMapping("/v1/users/me")
    public ResponseEntity<UserDetailResponseDTO> me() {
        return ResponseEntity.ok(userService.findCurrentUser());
    }
}
