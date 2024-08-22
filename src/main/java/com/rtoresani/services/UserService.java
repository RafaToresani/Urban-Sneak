package com.rtoresani.services;

import com.rtoresani.dtos.responses.UserResponse;

import java.util.List;

public interface UserService {
    List<UserResponse> findAllByRole(String role);
}
