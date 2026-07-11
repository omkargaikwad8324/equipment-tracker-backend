package com.shivswarajya.equipmenttracker.auth.service;

import com.shivswarajya.equipmenttracker.auth.dto.LoginRequest;
import com.shivswarajya.equipmenttracker.auth.dto.LoginResponse;
import com.shivswarajya.equipmenttracker.auth.dto.RegisterRequest;

public interface AuthService {

    void register(RegisterRequest request);

    LoginResponse login(LoginRequest request);

}