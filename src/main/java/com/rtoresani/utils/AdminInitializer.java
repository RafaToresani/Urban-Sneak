package com.rtoresani.utils;

import com.rtoresani.config.security.dtos.RegisterRequest;
import com.rtoresani.entities.user.ERole;
import com.rtoresani.entities.user.User;
import com.rtoresani.repositories.user.UserRepository;
import com.rtoresani.services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class AdminInitializer implements ApplicationRunner {
    @Autowired
    private AuthService authService;
    @Autowired
    private UserRepository userRepository;
    @Value("${admin.email}")
    private String email;
    @Value("${admin.password}")
    private String password;
    @Override
    public void run(ApplicationArguments args) throws Exception {
        if(userRepository.count()==0){
            RegisterRequest request = new RegisterRequest(email, password, "El Jefe", "Gorgory", null);
            this.authService.singUp(request);
            Optional<User> user = this.userRepository.findByEmail(email);
            if(user.isEmpty()) return;
            user.get().setRole(ERole.ADMIN);
            this.userRepository.save(user.get());
        }
    }
}
