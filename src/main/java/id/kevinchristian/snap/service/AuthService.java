package id.kevinchristian.snap.service;

import id.kevinchristian.snap.dto.request.UserCreateRequestDTO;

public interface AuthService {
    void signUp(UserCreateRequestDTO dto);
}
