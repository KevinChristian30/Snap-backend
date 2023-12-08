package id.kevinchristian.snap.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import id.kevinchristian.snap.dto.request.EmailAndPasswordAuthLoginRequestDTO;
import id.kevinchristian.snap.util.Constants;
import io.micrometer.common.util.StringUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.coyote.BadRequestException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import java.io.IOException;

public class EmailAndPasswordAuthProcessingFilter extends AbstractAuthenticationProcessingFilter {
    private final ObjectMapper objectMapper;
    private final AuthenticationSuccessHandler authenticationSuccessHandler;
    private final AuthenticationFailureHandler authenticationFailureHandler;

    public EmailAndPasswordAuthProcessingFilter(
            String defaultFilterProcessesUrl, ObjectMapper objectMapper,
            AuthenticationSuccessHandler authenticationSuccessHandler,
            AuthenticationFailureHandler authenticationFailureHandler) {
        super(defaultFilterProcessesUrl);
        this.objectMapper = objectMapper;
        this.authenticationSuccessHandler = authenticationSuccessHandler;
        this.authenticationFailureHandler = authenticationFailureHandler;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
        EmailAndPasswordAuthLoginRequestDTO dto = objectMapper.readValue(request.getReader(),
                EmailAndPasswordAuthLoginRequestDTO.class);
        if (StringUtils.isBlank(dto.email()) || StringUtils.isBlank(dto.password())) {
            throw new BadRequestException(Constants.ErrorMessage.Authentication.BAD_REQUEST);
        }

        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(dto.email(),
                dto.password());

        return getAuthenticationManager().authenticate(token);
    }

    @Override
    protected void successfulAuthentication(
            HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        authenticationSuccessHandler.onAuthenticationSuccess(request, response, authResult);
    }

    @Override
    protected void unsuccessfulAuthentication(
            HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        authenticationFailureHandler.onAuthenticationFailure(request, response, failed);
    }
}
