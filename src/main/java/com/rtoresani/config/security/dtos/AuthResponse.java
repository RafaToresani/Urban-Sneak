package com.rtoresani.config.security.dtos;

public record AuthResponse(
        String firstName,
        String lastName,
        String token,
        String role
) {
}
