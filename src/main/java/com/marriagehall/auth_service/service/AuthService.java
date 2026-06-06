package com.marriagehall.auth_service.service;

import com.marriagehall.auth_service.dto.LoginRequest;
import com.marriagehall.auth_service.dto.SignupRequest;
import com.marriagehall.auth_service.model.User;
import com.marriagehall.auth_service.repository.UserRepository;
import com.marriagehall.auth_service.util.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {
    
    public final UserRepository userRepository;
    public final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;

    public String register(@RequestBody SignupRequest request){
        if(userRepository.findByEmail(request.getEmail()).isPresent()){
            throw new RuntimeException("Email already exists");
        }
        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(request.getRole());
        userRepository.save(user);
        return "User Successfully Registered";
    }

    public String login(@RequestBody LoginRequest request){
        User user = userRepository.findByEmail(request.getEmail())
                    .orElseThrow(() -> new RuntimeException("User not found"));

        // 2 . Match Password
        if(!passwordEncoder.matches(request.getPassword(), user.getPassword())){
            throw new RuntimeException("Invalid Credentials");
        }
       // 3. Generate Token
        return jwtUtils.generateToken(user);
    }


}
