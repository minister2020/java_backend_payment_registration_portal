package com.example.AdvancedUser.service;

import com.example.AdvancedUser.dto.LoginRequest;
import com.example.AdvancedUser.dto.LoginResponse;
import com.example.AdvancedUser.model.User;
import com.example.AdvancedUser.repository.UserRepository;
import com.example.AdvancedUser.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    public LoginResponse login(LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        User user = userRepository.findByUsernameOrEmail(loginRequest.getUsername(), loginRequest.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!user.getIsActive()) {
            throw new RuntimeException("User account is not active");
        }

        if (user.getIsAdmin() == null || !user.getIsAdmin()) {
            throw new RuntimeException("Access denied. Admin privileges required.");
        }

        String token = jwtUtil.generateToken(user.getUsername());

        return new LoginResponse(token, user.getUsername(), user.getEmail(), user.getIsAdmin());
    }

    public User createAdminUser(String username, String email, String password) {
        if (userRepository.findByUsername(username).isPresent()) {
            throw new RuntimeException("Username already exists");
        }

        if (userRepository.findByEmail(email).isPresent()) {
            throw new RuntimeException("Email already exists");
        }

        User user = new User();
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));
        user.setIsAdmin(true);
        user.setIsActive(true);

        return userRepository.save(user);
    }
}


