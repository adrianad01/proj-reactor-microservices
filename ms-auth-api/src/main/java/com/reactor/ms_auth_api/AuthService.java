package com.reactor.ms_auth_api;

import java.util.HashMap;
import java.util.Map;

public class AuthService {
    private static Map<String, String> pragmaticos = new HashMap<>();

    static {
        pragmaticos.put("pragmatico1", "contraseña1");
        pragmaticos.put("pragmatico2", "contraseña2");
    }

    public static String login(String pragName, String password) {
        if (pragmaticos.containsKey(pragName) && pragmaticos.get(pragName).equals(password)) {
            return JWTUtil.generateToken(pragName);
        }
        return null;
    }

    public static boolean isAuthenticated(String token) {
        return JWTUtil.validateToken(token);
    }
}
