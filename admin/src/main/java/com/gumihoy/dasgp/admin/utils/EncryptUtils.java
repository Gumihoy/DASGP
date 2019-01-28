package com.gumihoy.dasgp.admin.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @author kongtong.ouyang on 2018/9/27.
 */
public final class EncryptUtils {

    private static final PasswordEncoder ENCODER = new BCryptPasswordEncoder();

    public static String encrypt(String rawPassword) {
        return ENCODER.encode(rawPassword);
    }

    public static boolean match(String rawPassword, String encodedPassword) {
        return ENCODER.matches(rawPassword, encodedPassword);
    }


}
