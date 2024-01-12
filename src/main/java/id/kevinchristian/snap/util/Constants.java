package id.kevinchristian.snap.util;

import java.util.Arrays;
import java.util.List;

public class Constants {
    Constants() throws IllegalAccessException {
        throw new IllegalAccessException(CONSTANTS);
    }

    public static final String CONSTANTS = "Constants";
    public static final String V1_URL = "/v1/**";
    public static final String SIGN_IN_URL = "/auth/sign-in";
    public static final String SIGN_UP_URL = "/auth/sign-up";
    public static final String SMTP_NO_REPLY_EMAIL = "no-reply.snap@outlook.com";

    public final static List<String> PERMIT_ENDPOINT_LIST = Arrays.asList(SIGN_IN_URL, "/swagger-ui.html",
            "/swagger-ui" + "/index.html", "/swagger-ui/index.css", "/favicon.ico", "/swagger-ui/swagger-ui.css",
            "/swagger-ui" + "/swagger-ui.css.map", "/swagger-ui/swagger-ui-standalone-preset.js", "/swagger-ui" +
                    "/swagger-ui-standalone" + "-preset.js.map",
            "/swagger-ui/swagger-ui-bundle.js", "/swagger-ui" +
                    "/swagger-ui-bundle.js.map",
            "/swagger" + "-ui/favicon-32x32.png", "/swagger-ui/favicon-16x16" +
                    ".png",
            "/swagger-ui/swagger-initializer.js", "/v3/api" + "-docs/swagger-config", "/v3/api-docs",
            SIGN_IN_URL, SIGN_UP_URL);
    public static final List<String> AUTHENTICATE_ENDPOINT_LIST = Arrays.asList(V1_URL);

    public static final class RequestHeaderKey {
        RequestHeaderKey() {
            throw new IllegalStateException(CONSTANTS);
        }

        public static final String AUTHORIZATION = "Authorization";
    }

    public static final class ResponseBodyKey {
        ResponseBodyKey() {
            throw new IllegalStateException(CONSTANTS);
        }

        public static final String MESSAGE = "message";
        public static final String TOKEN = "token";
    }

    public static final class ClaimsKey {
        ClaimsKey() {
            throw new IllegalStateException(CONSTANTS);
        }

        public static final String ROLES = "roles";
        public static final String USERNAME = "username";
        public static final String EMAIL = "email";
        public static final String IS_EMAIL_VERIFIED = "is_email_verified";
    }

    public static final class ErrorMessage {
        ErrorMessage() throws IllegalAccessException {
            throw new IllegalAccessException(CONSTANTS);
        }

        public static final class Authentication {
            Authentication() throws IllegalAccessException {
                throw new IllegalAccessException(CONSTANTS);
            }

            public static final String BAD_REQUEST = "Email and password should be provided";
            public static final String BAD_CREDENTIALS = "Invalid credentials";
            public static final String TOKEN_UNAVAILABLE = "Token should be provided";
            public static final String TOKEN_IS_INVALID = "Token is invalid";
            public static final String EMAIL_IS_REQUIRED = "Email is required";
            public static final String EMAIL_IS_INVALID = "Email is invalid";
            public static final String EMAIL_IS_TAKEN = "Email is taken";
            public static final String FIRST_NAME_IS_REQUIRED = "First name is required";
            public static final String USERNAME_IS_REQUIRED = "Username is required";
            public static final String USERNAME_IS_TAKEN = "Username is taken";
            public static final String PASSWORD_IS_REQUIRED = "Password is required";
            public static final String PASSWORD_CONFIRMATION_IS_REQUIRED = "Password confirmation is required";
            public static final String PASSWORD_IS_TOO_SHORT = "Password is too short";
            public static final String PASSWORD_MUST_CONTAIN_LOWERCASE_UPPERCASE_AND_NUMBERS = "Password must contain lowercase "
                    +
                    "letters, uppercase letters, and numbers";
            public static final String PASSWORD_MISMATCH = "Password confirmation doesn't match";
        }

        public static final class Service {
            Service() throws IllegalAccessException {
                throw new IllegalAccessException(CONSTANTS);
            }

            public static final class User {
                User() throws IllegalAccessException {
                    throw new IllegalAccessException(CONSTANTS);
                }

                public static final String USER_NOT_FOUND = "User not found";
            }

            public static final class Auth {
                Auth() throws IllegalAccessException {
                    throw new IllegalStateException();
                }

                public static final String CODE_INVALID = "Code is invalid";
                public static final String CODE_EXPIRED = "Code is invalid";
                public static final String EMAIL_CONFIRMED = "Your email is already confirmed";
            }

            public static final class Snap {
                Snap() throws IllegalAccessException {
                    throw new IllegalAccessException(CONSTANTS);
                }

                public static final String MEDIA_FILE_NOT_FOUND = "Media file is not found";
                public static final String SNAP_NOT_FOUND = "Snap not found";
            }
        }

        public static final class Snap {
            Snap() throws IllegalAccessException {
                throw new IllegalAccessException(CONSTANTS);
            }

            public static final String DESCRIPTION_IS_REQUIRED = "Description is required";
            public static final String MEDIA_FILE_ID_MUST_NOT_BE_BLANK = "Media file id must not be blank";
        }
    }

    public static final class Email {
        Email() {
            throw new IllegalStateException(CONSTANTS);
        }

        public static final String EMAIL_CONFIRMATION_SUBJECT = "Confirm Your Email";
    }
}
