package com.ouronet.ouronet.controllers;

import com.ouronet.ouronet.models.AuthenticationRequest;
import com.ouronet.ouronet.models.User;
import com.ouronet.ouronet.services.JwtService;
import com.ouronet.ouronet.usecase.UserControllerUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserControllerUseCase userControllerUseCase;

    @Autowired
    private JwtService jwtUtil;

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody User user) {
        userControllerUseCase.registerUser(user);
        return ResponseEntity.ok("User registered successfully!");
    }

    @PostMapping("/login")
    public ResponseEntity<String> loginUser(@RequestBody AuthenticationRequest request) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
            );
            // Kullanıcı detaylarını çekiyoruz
            UserDetails userDetails = (UserDetails) userControllerUseCase.getUserByName(request.getUsername());
            // Token üretiyoruz
            String token = JwtService.generateToken(userDetails.getUsername().concat(userDetails.getPassword()));
            return ResponseEntity.ok(token);
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
        }
    }
}