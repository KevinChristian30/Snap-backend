package id.kevinchristian.snap.service.impl;

import id.kevinchristian.snap.config.properties.ApplicationProperties;
import id.kevinchristian.snap.domain.EmailConfirmationCode;
import id.kevinchristian.snap.domain.User;
import id.kevinchristian.snap.dto.request.EmailConfirmationCodeVerifyRequestDTO;
import id.kevinchristian.snap.dto.request.UserCreateRequestDTO;
import id.kevinchristian.snap.dto.response.UserResponseDTO;
import id.kevinchristian.snap.exception.BadRequestException;
import id.kevinchristian.snap.exception.ResourceNotFoundException;
import id.kevinchristian.snap.repository.EmailConfirmationCodeRepository;
import id.kevinchristian.snap.repository.UserRepository;
import id.kevinchristian.snap.security.model.SnapUserDetails;
import id.kevinchristian.snap.service.AuthService;
import id.kevinchristian.snap.util.CodeUtil;
import id.kevinchristian.snap.util.Constants;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

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
    private final EmailConfirmationCodeRepository emailConfirmationCodeRepository;
    private final PasswordEncoder passwordEncoder;
    private final JavaMailSender mailSender;
    private final ApplicationProperties applicationProperties;

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
    public User findCurrentUser() {
        UserResponseDTO userResponseDTO = findCurrentUserDetails();
        return userRepository.findByEmail(userResponseDTO.email())
                .orElseThrow(() -> new ResourceNotFoundException(Constants.ErrorMessage.Service.User.USER_NOT_FOUND));
    }

    @Override
    public void sendEmailConfirmationCode() {
        User user = findCurrentUser();
        String code = CodeUtil.generateCode();

        createOrUpdateEmailConfirmationCode(user, code);
        sendConfirmationCodeToEmail(user, code);
    }

    private void createOrUpdateEmailConfirmationCode(User user, String code) {
        EmailConfirmationCode emailConfirmationCode = emailConfirmationCodeRepository.findByUser(user).orElse(null);
        if (emailConfirmationCode == null) {
            emailConfirmationCode = new EmailConfirmationCode();
            emailConfirmationCode.setUser(user);
            emailConfirmationCode.setCode(code);
            emailConfirmationCode.setLastRequestedAt(LocalDateTime.now());
        } else {
            if (ChronoUnit.MINUTES.between(emailConfirmationCode.getLastRequestedAt(),
                    LocalDateTime.now()) <= applicationProperties.getEmailConfirmationRequestIntervalInMinutes()) {
                throw new BadRequestException(String.format("You can only request a new code every %d minutes",
                        applicationProperties.getEmailConfirmationRequestIntervalInMinutes()));
            }

            emailConfirmationCode.setCode(code);
            emailConfirmationCode.setLastRequestedAt(LocalDateTime.now());
        }

        emailConfirmationCodeRepository.save(emailConfirmationCode);
    }

    private void sendConfirmationCodeToEmail(User user, String code) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom(Constants.SMTP_NO_REPLY_EMAIL);

        simpleMailMessage.setTo(user.getEmail());
        simpleMailMessage.setSubject(Constants.Email.EMAIL_CONFIRMATION_SUBJECT);
        simpleMailMessage.setText(String.format(
                "Your confirmation code is %s\nThe verification code will be valid for %d minutes. Please do not share this code with anyone.\n\nThis is an automated message, please do not reply.",
                code, applicationProperties.getEmailConfirmationCodeValidityDurationInMinutes()));

        mailSender.send(simpleMailMessage);
    }

    @Override
    public void verifyEmailConfirmationCode(EmailConfirmationCodeVerifyRequestDTO dto) {
        User user = findCurrentUser();
        EmailConfirmationCode emailConfirmationCode = emailConfirmationCodeRepository.findByUser(user)
                .orElseThrow(() -> new ResourceNotFoundException(Constants.ErrorMessage.Service.User.USER_NOT_FOUND));

        if (!emailConfirmationCode.getCode().equals(dto.code())) {
            throw new BadRequestException(Constants.ErrorMessage.Service.Auth.CODE_INVALID);
        }

        if (ChronoUnit.MINUTES.between(emailConfirmationCode.getLastRequestedAt(),
                LocalDateTime.now()) > applicationProperties.getEmailConfirmationCodeValidityDurationInMinutes()) {
            throw new BadRequestException(Constants.ErrorMessage.Service.Auth.CODE_EXPIRED);
        }

        user.setEmailConfirmed(true);
        userRepository.save(user);
    }
}
