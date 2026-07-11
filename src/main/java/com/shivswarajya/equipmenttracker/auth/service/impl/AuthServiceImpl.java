package com.shivswarajya.equipmenttracker.auth.service.impl;

import com.shivswarajya.equipmenttracker.auth.dto.LoginRequest;
import com.shivswarajya.equipmenttracker.auth.dto.LoginResponse;
import com.shivswarajya.equipmenttracker.auth.dto.RegisterRequest;
import com.shivswarajya.equipmenttracker.auth.entity.User;
import com.shivswarajya.equipmenttracker.auth.repository.UserRepository;
import com.shivswarajya.equipmenttracker.auth.service.AuthService;
import com.shivswarajya.equipmenttracker.exception.BadRequestException;

import lombok.RequiredArgsConstructor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.shivswarajya.equipmenttracker.auth.security.JwtService;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private static final Logger log =
        LoggerFactory.getLogger(AuthServiceImpl.class);

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @Override
    public void register(RegisterRequest request) {

        if (userRepository.existsByUsername(request.getUsername())) {
            throw new BadRequestException("Username already exists.");
        }

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new BadRequestException("Email already exists.");
        }

        User user = User.builder()
                .fullName(request.getFullName())
                .username(request.getUsername())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(request.getRole())
                .enabled(true)
                .build();

        userRepository.save(user);
    }

    @Override
    public LoginResponse login(LoginRequest request) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()));

        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new BadRequestException("User not found"));

        String token = jwtService.generateToken(user.getUsername());

        log.info("User {} logged in successfully", user.getUsername());

        
        return LoginResponse.builder()
                .token(token)
                .username(user.getUsername())
                .fullName(user.getFullName())
                .role(user.getRole())
                .build();
    }
}