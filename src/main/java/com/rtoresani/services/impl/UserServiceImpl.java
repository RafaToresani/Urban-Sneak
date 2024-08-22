package com.rtoresani.services.impl;

import com.rtoresani.dtos.responses.UserResponse;
import com.rtoresani.entities.user.ERole;
import com.rtoresani.entities.user.User;
import com.rtoresani.repositories.user.UserRepository;
import com.rtoresani.services.UserService;
import com.rtoresani.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Override
    public List<UserResponse> findAllByRole(String role) {
        Utils.validateRole(role);
        return this.userRepository.findAllByRole(ERole.valueOf(role)).stream().map(this::userToResponse).toList();
    }

    UserResponse userToResponse(User user){
        return new UserResponse(
                user.getEmail(),
                user.getUserInfo().getFirstName(),
                user.getUserInfo().getFirstName(),
                user.getUserInfo().getDni(),
                user.getRole().name(),
                user.getUserInfo().getPhoneNumber());
    }
}
