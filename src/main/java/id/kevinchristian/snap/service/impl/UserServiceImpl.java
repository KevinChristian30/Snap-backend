package id.kevinchristian.snap.service.impl;

import id.kevinchristian.snap.domain.User;
import id.kevinchristian.snap.dto.response.UserDetailResponseDTO;
import id.kevinchristian.snap.exception.ResourceNotFoundException;
import id.kevinchristian.snap.repository.UserRepository;
import id.kevinchristian.snap.service.UserService;
import id.kevinchristian.snap.service.AuthService;
import id.kevinchristian.snap.util.Constants;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final AuthService authService;

    @Override
    public UserDetails findByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException(Constants.ErrorMessage.Service.User.USER_NOT_FOUND));
    }

    @Override
    public UserDetailResponseDTO findCurrentUser() {
        User currentUser = authService.findCurrentUser();
        return new UserDetailResponseDTO(
            currentUser.getSecureId(),
            currentUser.getEmail(),
            currentUser.getFirstName(),
            currentUser.getLastName(),
            currentUser.getUsername(),
            currentUser.getBio()
        );
    }
}
