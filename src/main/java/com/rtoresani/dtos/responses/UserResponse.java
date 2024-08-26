package com.rtoresani.dtos.responses;

public record UserResponse(
        String email,
        String firstName,
        String lastName,
        String dni,
        String role,
        String phoneNumber
) {
}
