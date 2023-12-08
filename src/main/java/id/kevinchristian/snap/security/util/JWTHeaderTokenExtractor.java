package id.kevinchristian.snap.security.util;

import id.kevinchristian.snap.util.Constants;
import io.micrometer.common.util.StringUtils;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.stereotype.Component;

@Component
public class JWTHeaderTokenExtractor implements TokenExtractor {
    private static final String HEADER_PREFIX = "Bearer ";

    @Override
    public String extract(String payload) {
        if (StringUtils.isBlank(payload)) {
            throw new AuthenticationServiceException(Constants.ErrorMessage.Authentication.TOKEN_UNAVAILABLE);
        }

        if (payload.length() < HEADER_PREFIX.length()) {
            throw new AuthenticationServiceException(Constants.ErrorMessage.Authentication.INVALID_TOKEN);
        }

        return payload.substring(HEADER_PREFIX.length());
    }
}
