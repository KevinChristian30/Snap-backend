package id.kevinchristian.snap.security.filter;

import id.kevinchristian.snap.security.model.AnonymousAuthentication;
import id.kevinchristian.snap.security.model.JWTAuthToken;
import id.kevinchristian.snap.security.model.RawJWTAccessToken;
import id.kevinchristian.snap.security.util.TokenExtractor;
import id.kevinchristian.snap.util.Constants;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.util.matcher.RequestMatcher;

import java.io.IOException;

public class JWTAuthProcessingFilter extends AbstractAuthenticationProcessingFilter {
    private final TokenExtractor tokenExtractor;
    private final AuthenticationFailureHandler authenticationFailureHandler;

    public JWTAuthProcessingFilter(
            RequestMatcher requiresAuthenticationRequestMatcher, TokenExtractor tokenExtractor,
            AuthenticationFailureHandler authenticationFailureHandler) {
        super(requiresAuthenticationRequestMatcher);
        this.tokenExtractor = tokenExtractor;
        this.authenticationFailureHandler = authenticationFailureHandler;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
        String authorizationHeader = request.getHeader(Constants.RequestHeaderKey.AUTHORIZATION);
        String jwt = tokenExtractor.extract(authorizationHeader);

        RawJWTAccessToken rawJWTAccessToken = new RawJWTAccessToken(jwt);

        return getAuthenticationManager().authenticate(new JWTAuthToken(rawJWTAccessToken));
    }

    @Override
    protected void successfulAuthentication(
            HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(authResult);
        SecurityContextHolder.setContext(context);
        chain.doFilter(request, response);
    }

    @Override
    protected void unsuccessfulAuthentication(
            HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        SecurityContextHolder.clearContext();
        SecurityContextHolder.getContext().setAuthentication(new AnonymousAuthentication());
        authenticationFailureHandler.onAuthenticationFailure(request, response, failed);
    }
}
