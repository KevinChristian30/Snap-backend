package id.kevinchristian.snap.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import id.kevinchristian.snap.security.filter.EmailAndPasswordAuthProcessingFilter;
import id.kevinchristian.snap.security.filter.JWTAuthProcessingFilter;
import id.kevinchristian.snap.security.handler.EmailAndPasswordAuthFailureHandler;
import id.kevinchristian.snap.security.handler.EmailAndPasswordAuthSuccessHandler;
import id.kevinchristian.snap.security.provider.EmailAndPasswordAuthProvider;
import id.kevinchristian.snap.security.provider.JWTAuthProvider;
import id.kevinchristian.snap.security.util.JWTTokenFactory;
import id.kevinchristian.snap.security.util.SkipPathRequestMatcher;
import id.kevinchristian.snap.security.util.TokenExtractor;
import id.kevinchristian.snap.util.Constants;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
@EnableMethodSecurity
@Configuration
@AllArgsConstructor
public class SecurityConfig {
    private final EmailAndPasswordAuthProvider emailAndPasswordAuthProvider;
    private final JWTAuthProvider jwtAuthProvider;

    @Bean
    public AuthenticationSuccessHandler authenticationSuccessHandler(
            ObjectMapper objectMapper, JWTTokenFactory jwtTokenFactory) {
        return new EmailAndPasswordAuthSuccessHandler(objectMapper, jwtTokenFactory);
    }

    @Bean
    public AuthenticationFailureHandler authenticationFailureHandler(ObjectMapper objectMapper) {
        return new EmailAndPasswordAuthFailureHandler(objectMapper);
    }

    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public JWTAuthProcessingFilter jwtAuthProcessingFilter(
            TokenExtractor tokenExtractor, AuthenticationFailureHandler authenticationFailureHandler,
            AuthenticationManager authenticationManager) {
        SkipPathRequestMatcher skipPathRequestMatcher = new SkipPathRequestMatcher(Constants.PERMIT_ENDPOINT_LIST,
                Constants.AUTHENTICATE_ENDPOINT_LIST);
        JWTAuthProcessingFilter filter = new JWTAuthProcessingFilter(skipPathRequestMatcher, tokenExtractor,
                authenticationFailureHandler);
        filter.setAuthenticationManager(authenticationManager);

        return filter;
    }

    @Bean
    public EmailAndPasswordAuthProcessingFilter emailAndPasswordAuthProcessingFilter(
            ObjectMapper objectMapper, AuthenticationSuccessHandler authenticationSuccessHandler,
            AuthenticationFailureHandler authenticationFailureHandler, AuthenticationManager authenticationManager) {
        EmailAndPasswordAuthProcessingFilter filter = new EmailAndPasswordAuthProcessingFilter(Constants.SIGN_IN_URL,
                objectMapper, authenticationSuccessHandler, authenticationFailureHandler);
        filter.setAuthenticationManager(authenticationManager);
        return filter;
    }

    @Autowired
    void registerProvider(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        authenticationManagerBuilder.authenticationProvider(emailAndPasswordAuthProvider).authenticationProvider(jwtAuthProvider);
    }

    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity httpSecurity, EmailAndPasswordAuthProcessingFilter emailAndPasswordAuthProcessingFilter,
            JWTAuthProcessingFilter jwtAuthProcessingFilter) throws Exception {
        httpSecurity
            .csrf(AbstractHttpConfigurer::disable)
            .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests(auth ->
                auth.requestMatchers(Constants.PERMIT_ENDPOINT_LIST.toArray(new String[0])).permitAll()
                    .requestMatchers(Constants.AUTHENTICATE_ENDPOINT_LIST.toArray(new String[0])).authenticated()
            );

        httpSecurity.addFilterBefore(emailAndPasswordAuthProcessingFilter, UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(jwtAuthProcessingFilter, UsernamePasswordAuthenticationFilter.class);
        return httpSecurity.build();
    }
}
