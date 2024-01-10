package id.kevinchristian.snap.service;

import id.kevinchristian.snap.domain.User;
import id.kevinchristian.snap.dto.request.EmailConfirmationCodeVerifyRequestDTO;
import id.kevinchristian.snap.dto.request.UserCreateRequestDTO;
import id.kevinchristian.snap.dto.response.UserResponseDTO;

public interface AuthService {
    void signUp(UserCreateRequestDTO dto);

    UserResponseDTO findCurrentUserDetails();

    User findCurrentUser();

    void sendEmailConfirmationCode();

    void verifyEmailConfirmationCode(EmailConfirmationCodeVerifyRequestDTO dto);
}
