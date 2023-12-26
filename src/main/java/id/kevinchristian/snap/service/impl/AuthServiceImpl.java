package id.kevinchristian.snap.service.impl;

import id.kevinchristian.snap.domain.User;
import id.kevinchristian.snap.dto.request.UserCreateRequestDTO;
import id.kevinchristian.snap.dto.response.UserResponseDTO;
import id.kevinchristian.snap.exception.BadRequestException;
import id.kevinchristian.snap.repository.UserRepository;
import id.kevinchristian.snap.security.model.SnapUserDetails;
import id.kevinchristian.snap.service.AuthService;
import id.kevinchristian.snap.util.Constants;
import lombok.AllArgsConstructor;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JavaMailSender mailSender;

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

    @Override
    public UserResponseDTO findCurrentUserDetails() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        SnapUserDetails user = (SnapUserDetails) securityContext.getAuthentication().getPrincipal();

        return new UserResponseDTO(user.getSubject(),
                user.getGrantedAuthorities().stream().map(ga -> ga.getAuthority()).toList(),
                user.getUsername(), user.getIsEmailVerified());
    }

    @Override
    public void sendEmailConfirmation() {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom("kdotchrist30@gmail.com");
        simpleMailMessage.setTo("kdotchrist30@gmail.com");
        simpleMailMessage.setText("Test Email Body");
        simpleMailMessage.setSubject("Test Email Subject");

        mailSender.send(simpleMailMessage);
    }
}
