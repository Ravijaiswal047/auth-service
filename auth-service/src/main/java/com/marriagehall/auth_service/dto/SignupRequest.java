package com.marriagehall.auth_service.dto;

import com.marriagehall.auth_service.enums.Role;
import lombok.Data;

@Data
public class SignupRequest {
    private String name;
    private String email;
    private String password;
    private Role role;
}

