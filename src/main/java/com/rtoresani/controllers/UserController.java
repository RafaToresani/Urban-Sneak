package com.rtoresani.controllers;

import com.rtoresani.dtos.responses.UserResponse;
import com.rtoresani.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {
    @Autowired
    private UserService userService;

    //      G E T
    @GetMapping("/{role}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAnyAuthority('ADMIN', 'MANAGER')")
    public List<UserResponse> findAllCustomers(@PathVariable(name="role") String role){
        return this.userService.findAllByRole(role);
    }

}
