package com.ariffugur.onlineFoodOrdering.controller;

import com.ariffugur.onlineFoodOrdering.dto.AuthRequest;
import com.ariffugur.onlineFoodOrdering.model.User;
import com.ariffugur.onlineFoodOrdering.response.AuthResponse;
import com.ariffugur.onlineFoodOrdering.service.JwtService;
import com.ariffugur.onlineFoodOrdering.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public AuthController(UserService userService, AuthenticationManager authenticationManager, JwtService jwtService) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }


    @PostMapping("/register")
    public User createUserHandler(@RequestBody User user) {
        return userService.createUser(user);
    }

    @PostMapping("/generateToken")
    public String generateToken(@RequestBody AuthRequest request) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.username(), request.password()));
        if (authentication.isAuthenticated()) {
            return "Generated Token:\n" + jwtService.generateToken(request.username());
        }
        throw new UsernameNotFoundException("invalid username {} " + request.username());
    }
}
