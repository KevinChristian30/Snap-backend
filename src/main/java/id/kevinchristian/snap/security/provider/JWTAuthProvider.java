package id.kevinchristian.snap.security.provider;

import id.kevinchristian.snap.security.model.JWTAuthToken;
import id.kevinchristian.snap.security.model.RawJWTAccessToken;
import id.kevinchristian.snap.util.Constants;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class JWTAuthProvider implements AuthenticationProvider {
    private final Key key;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        RawJWTAccessToken token = (RawJWTAccessToken) authentication.getCredentials();
        Jws<Claims> jwsClaims = token.parseClaims(key);
        String subject = jwsClaims.getBody().getSubject();
        List<String> roles = jwsClaims.getBody().get(Constants.ClaimsKey.ROLES, List.class);
        List<GrantedAuthority> grantedAuthorities =
                roles.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());

        UserDetails userDetails = new UserDetails() {
            @Override
            public Collection<? extends GrantedAuthority> getAuthorities() {
                return grantedAuthorities;
            }

            @Override
            public String getPassword() {
                return null;
            }

            @Override
            public String getUsername() {
                return subject;
            }

            @Override
            public boolean isAccountNonExpired() {
                return true;
            }

            @Override
            public boolean isAccountNonLocked() {
                return true;
            }

            @Override
            public boolean isCredentialsNonExpired() {
                return true;
            }

            @Override
            public boolean isEnabled() {
                return true;
            }
        };

        return new JWTAuthToken(userDetails, grantedAuthorities);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return JWTAuthToken.class.isAssignableFrom(authentication);
    }
}