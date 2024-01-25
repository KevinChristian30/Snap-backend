package id.kevinchristian.snap.service;

import org.springframework.security.core.userdetails.UserDetails;

import id.kevinchristian.snap.dto.response.UserDetailResponseDTO;

public interface UserService {
    UserDetails findByEmail(String email);

    UserDetailResponseDTO findCurrentUser();
}
