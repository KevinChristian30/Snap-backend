package id.kevinchristian.snap.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import id.kevinchristian.snap.security.filter.EmailAndPasswordAuthProcessingFilter;
import id.kevinchristian.snap.security.handler.EmailAndPasswordAuthFailureHandler;
import id.kevinchristian.snap.security.handler.EmailAndPasswordAuthSuccessHandler;
import id.kevinchristian.snap.security.provider.EmailAndPasswordAuthProvider;
import id.kevinchristian.snap.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
@EnableMethodSecurity
@Configuration
public class SecurityConfig {
    @Autowired
    private EmailAndPasswordAuthProvider emailAndPasswordAuthProvider;

    @Bean
    public AuthenticationSuccessHandler authenticationSuccessHandler(ObjectMapper objectMapper) {
        return new EmailAndPasswordAuthSuccessHandler(objectMapper);
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
    public EmailAndPasswordAuthProcessingFilter emailAndPasswordAuthProcessingFilter(
            ObjectMapper objectMapper, AuthenticationSuccessHandler authenticationSuccessHandler,
            AuthenticationFailureHandler authenticationFailureHandler, AuthenticationManager authenticationManager) {
        EmailAndPasswordAuthProcessingFilter filter = new EmailAndPasswordAuthProcessingFilter(Constants.AUTH_URL,
                objectMapper, authenticationSuccessHandler, authenticationFailureHandler);
        filter.setAuthenticationManager(authenticationManager);
        return filter;
    }

    @Autowired
    void registerProvider(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        authenticationManagerBuilder.authenticationProvider(emailAndPasswordAuthProvider);
    }

    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity httpSecurity, EmailAndPasswordAuthProcessingFilter emailAndPasswordAuthProcessingFilter) throws Exception {
        httpSecurity.authorizeHttpRequests().requestMatchers(Constants.PERMIT_ENDPOINT_LIST.toArray(new String[0])).permitAll().requestMatchers(Constants.V1_URL).authenticated().and().csrf().disable().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and().httpBasic();

        httpSecurity.addFilterBefore(emailAndPasswordAuthProcessingFilter, UsernamePasswordAuthenticationFilter.class);
        return httpSecurity.build();
    }
}
