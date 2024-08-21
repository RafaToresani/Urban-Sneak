package com.rtoresani.services.impl;

import com.rtoresani.config.security.dtos.AuthResponse;
import com.rtoresani.config.security.dtos.LoginRequest;
import com.rtoresani.config.security.dtos.RegisterRequest;
import com.rtoresani.config.security.jwt.JwtService;
import com.rtoresani.entities.cart.Cart;
import com.rtoresani.entities.user.Address;
import com.rtoresani.entities.user.ERole;
import com.rtoresani.entities.user.User;
import com.rtoresani.entities.user.UserInfo;
import com.rtoresani.exceptions.ResourceAlreadyExistsException;
import com.rtoresani.exceptions.ResourceNotFoundException;
import com.rtoresani.repositories.user.UserRepository;
import com.rtoresani.services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Optional;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JwtService jwtService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AuthenticationManager authenticationManager;


    @Override
    public AuthResponse logIn(LoginRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.email(), request.password()));
        Optional<User> user = userRepository.findByEmail(request.email());
        if(user.isEmpty()) throw new ResourceNotFoundException("Email doesn't exists");

        String token = jwtService.getToken(user.get(), user.get().getAuthorities());

        return new AuthResponse(user.get().getUserInfo().getFirstName(), user.get().getUserInfo().getLastName(), token, user.get().getRole().name());
    }

    @Override
    public AuthResponse singUp(RegisterRequest request) {
        Optional<User> opt = this.userRepository.findByEmail(request.email());
        if(opt.isPresent()) throw new ResourceAlreadyExistsException("User email already exists.");

        User user = User.builder()
                .email(request.email())
                .password(passwordEncoder.encode(request.password()))
                .role(ERole.CUSTOMER)
                .cart(Cart.builder().items(new HashSet<>()).lastUpdate(LocalDateTime.now()).build())
                .build();

        UserInfo userInfo = UserInfo.builder()
                .firstName(request.firstName())
                .lastName(request.lastName())
                .user(user)
                .address(new HashSet<>())
                .build();

        user.setUserInfo(userInfo);

        this.userRepository.save(user);

        return new AuthResponse(
                user.getUserInfo().getFirstName(),
                user.getUserInfo().getLastName(),
                jwtService.getToken(user, user.getAuthorities())
                , user.getRole().name());
    }
}
