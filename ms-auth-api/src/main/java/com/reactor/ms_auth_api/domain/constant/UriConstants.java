package com.reactor.ms_auth_api.domain.constant;

public class UriConstants {
    public static final String AUTH_LOGIN = "/auth/login";
    public static final String AUTH_VALIDATE = "/auth/validate";
    public static final String PROTECTED_ROUTE = "/protected";

    private UriConstants() {
        throw new IllegalStateException("Utility class");
    }
}
