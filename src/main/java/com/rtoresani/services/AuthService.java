package com.rtoresani.services;

import com.rtoresani.config.security.dtos.AuthResponse;
import com.rtoresani.config.security.dtos.LoginRequest;
import com.rtoresani.config.security.dtos.RegisterRequest;

public interface AuthService {
    AuthResponse singUp(RegisterRequest request);

    AuthResponse logIn(LoginRequest request);
}
