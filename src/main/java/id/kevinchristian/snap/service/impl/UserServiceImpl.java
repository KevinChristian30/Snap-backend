package id.kevinchristian.snap.service.impl;

import id.kevinchristian.snap.exception.ResourceNotFoundException;
import id.kevinchristian.snap.repository.UserRepository;
import id.kevinchristian.snap.service.UserService;
import id.kevinchristian.snap.util.Constants;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    public UserDetails findByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException(Constants.ErrorMessage.Service.User.USER_NOT_FOUND));
    }
}
