package com.rtoresani.utils;

import com.rtoresani.entities.user.ERole;

public class Utils {
    public static void validateRole(String role){
        try {
            ERole.valueOf(role);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("The provided role is not valid: " + role);
        }
    }
}
