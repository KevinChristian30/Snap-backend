package id.kevinchristian.snap.util;

import java.util.Arrays;
import java.util.List;

public class Constants {
    Constants() throws IllegalAccessException {
        throw new IllegalAccessException(CONSTANTS);
    }

    public static final String CONSTANTS = "Constants";
    public static final String V1_URL = "/v1/**";
    public static final String AUTH_URL = "/v1/auth/sign-in";
    public final static List<String> PERMIT_ENDPOINT_LIST = Arrays.asList(AUTH_URL,
            "/swagger-ui.html"
            , "/swagger-ui/index.html"
            , "/swagger-ui/index.css"
            , "/favicon.ico"
            , "/swagger-ui/swagger-ui.css"
            , "/swagger-ui/swagger-ui.css.map"
            , "/swagger-ui/swagger-ui-standalone-preset.js"
            , "/swagger-ui/swagger-ui-standalone-preset.js.map"
            , "/swagger-ui/swagger-ui-bundle.js"
            , "/swagger-ui/swagger-ui-bundle.js.map"
            , "/swagger-ui/favicon-32x32.png"
            , "/swagger-ui/favicon-16x16.png"
            , "/swagger-ui/swagger-initializer.js"
            , "/v3/api-docs/swagger-config"
            , "/v3/api-docs"
            , AUTH_URL
    );
    private static final List<String> AUTHENTICATE_ENDPOINT_LIST = Arrays.asList(V1_URL);

    public static final class ErrorMessage {
        ErrorMessage() throws IllegalAccessException {
            throw new IllegalAccessException(CONSTANTS);
        }

        public static final class Authentication {
            Authentication() throws IllegalAccessException {
                throw new IllegalAccessException(CONSTANTS);
            }

            public static final String BAD_REQUEST = "email.and.password.should.be.provided";
            public static final String BAD_CREDENTIALS = "email.and.password.is.incorrect";
        }

        public static final class Service {
            Service() throws IllegalAccessException {
                throw new IllegalAccessException(CONSTANTS);
            }

            public static final class User {
                User() throws IllegalAccessException {
                    throw new IllegalAccessException(CONSTANTS);
                }

                public static final String USER_NOT_FOUND = "user.not.found";
            }
        }
    }
}
