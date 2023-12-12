package id.kevinchristian.snap.service.impl;

import id.kevinchristian.snap.domain.User;
import id.kevinchristian.snap.dto.request.UserCreateRequestDTO;
import id.kevinchristian.snap.exception.BadRequestException;
import id.kevinchristian.snap.repository.UserRepository;
import id.kevinchristian.snap.service.AuthService;
import id.kevinchristian.snap.util.Constants;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void signUp(UserCreateRequestDTO dto) {
        if (!dto.password().equals(dto.confirmPassword())) {
            throw new BadRequestException(Constants.ErrorMessage.Authentication.PASSWORD_MISMATCH);
        }

        User user = User.builder()
                .email(dto.email())
                .password(passwordEncoder.encode(dto.password()))
                .firstName(dto.firstName())
                .emailConfirmed(false)
                .build();
        userRepository.save(user);
    }
}
