package com.shivswarajya.equipmenttracker.auth.dto;

import com.shivswarajya.equipmenttracker.auth.entity.Role;
import lombok.Data;

@Data
public class RegisterRequest {

    private String fullName;

    private String username;

    private String email;

    private String password;

    private Role role;
}