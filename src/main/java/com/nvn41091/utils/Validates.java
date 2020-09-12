package com.nvn41091.utils;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class Validates {
    public static boolean isEmpty(String obj) {
        if (StringUtils.isEmpty( obj )) {
            return true;
        }
        return false;
    }

    public static boolean checkLength(String obj, int min, int max) {
        if (obj.length() > min && obj.length() < max) {
            return true;
        }
        return false;
    }

    public static boolean checkLengthMax(String obj, int min) {
        if (obj.length() > min ) {
            return true;
        }
        return false;
    }

    public static boolean checkResetPass(String oldPass, String newPass){
        if(oldPass.equals( newPass )){
            return true;
        }
        return false;
    }
    public static Boolean changePassword(String encryptedPassword, String unencryptedPassword) {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        CharSequence s = unencryptedPassword;
        Boolean hashedPassword = passwordEncoder.matches( s, encryptedPassword );
        return hashedPassword;
    }

    public static boolean checkFormatEmail(String email) {
        if (email.matches("^[a-z][a-z0-9_\\.]{5,32}@[a-z0-9]{2,}(\\.[a-z0-9]{2,4}){1,2}$")) {
            return true;
        } else {
            return false;
        }
    }

}
