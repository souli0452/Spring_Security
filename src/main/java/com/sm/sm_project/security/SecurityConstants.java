package com.sm.sm_project.security;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


public class SecurityConstants {
    public static final String SECRET = "SM@Felix/Ismael";
    public static final long EXPIRATION_TIME = 100_000_000; // 10 days
    public static final String  TOKEN_PREFIX = "SM ";
    public static final String HEADER_STRING = "Authorization";
}
