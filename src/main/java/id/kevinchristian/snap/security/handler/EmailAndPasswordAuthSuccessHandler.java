package id.kevinchristian.snap.security.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import id.kevinchristian.snap.security.model.JWTAccessToken;
import id.kevinchristian.snap.security.util.JWTTokenFactory;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@AllArgsConstructor
public class EmailAndPasswordAuthSuccessHandler implements AuthenticationSuccessHandler {
    private final ObjectMapper objectMapper;
    private final JWTTokenFactory jwtTokenFactory;

    @Override
    public void onAuthenticationSuccess(
            HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        JWTAccessToken token = jwtTokenFactory.createJWTAccessToken(userDetails.getUsername(),
                userDetails.getAuthorities());

        Map<String, String> result = new HashMap<>();
        result.put("result", token.getToken());

        response.setStatus(HttpStatus.OK.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        objectMapper.writeValue(response.getWriter(), result);
    }
}
