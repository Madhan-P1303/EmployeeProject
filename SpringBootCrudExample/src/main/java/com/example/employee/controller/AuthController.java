package com.example.employee.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import com.example.employee.dto.*;
import com.example.employee.entity.User;
import com.example.employee.repository.UserRepository;
import com.example.employee.security.JwtUtil;
import com.example.employee.util.PasswordValidator;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*", maxAge = 3600)
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private PasswordValidator passwordValidator;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        try {
            User user = userRepository.findByUsername(loginRequest.getUsername()).orElse(null);
            
            if (user == null) {
                return ResponseEntity.badRequest().body("Invalid credentials");
            }

            // Check if account is locked
            if (user.isAccountLocked()) {
                if (user.getAccountLockedTime() != null && 
                    LocalDateTime.now().isAfter(user.getAccountLockedTime().plusMinutes(30))) {
                    // Auto-unlock after 30 minutes
                    user.setAccountLocked(false);
                    user.setFailedLoginAttempts(0);
                    user.setAccountLockedTime(null);
                    userRepository.save(user);
                } else {
                    return ResponseEntity.badRequest().body("Account is locked due to too many failed login attempts. Please try again later.");
                }
            }

            Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword())
            );

            // Reset failed attempts on successful login
            user.setFailedLoginAttempts(0);
            user.setLastLoginTime(LocalDateTime.now());
            userRepository.save(user);

            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            String jwt = jwtUtil.generateToken(userDetails);

            return ResponseEntity.ok(new JwtResponse(jwt, userDetails.getUsername(), user.getEmail(), user.getRole()));
            
        } catch (BadCredentialsException e) {
            // Handle failed login attempts
            User user = userRepository.findByUsername(loginRequest.getUsername()).orElse(null);
            if (user != null) {
                user.setFailedLoginAttempts(user.getFailedLoginAttempts() + 1);
                if (user.getFailedLoginAttempts() >= 5) {
                    user.setAccountLocked(true);
                    user.setAccountLockedTime(LocalDateTime.now());
                }
                userRepository.save(user);
            }
            return ResponseEntity.badRequest().body("Invalid credentials");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Login failed: " + e.getMessage());
        }
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody SignupRequest signupRequest) {
        if (userRepository.existsByUsername(signupRequest.getUsername())) {
            return ResponseEntity.badRequest().body("Username already exists!");
        }

        if (userRepository.existsByEmail(signupRequest.getEmail())) {
            return ResponseEntity.badRequest().body("Email already exists!");
        }

        // Validate password strength
        if (!passwordValidator.isValid(signupRequest.getPassword())) {
            return ResponseEntity.badRequest().body(passwordValidator.getPasswordRequirements());
        }

        // Validate role
        if (!isValidRole(signupRequest.getRole())) {
            return ResponseEntity.badRequest().body("Invalid role. Allowed roles: ADMIN, HR, USER");
        }

        User user = new User(signupRequest.getUsername(),
                passwordEncoder.encode(signupRequest.getPassword()),
                signupRequest.getEmail(),
                signupRequest.getRole());

        userRepository.save(user);

        return ResponseEntity.ok("User registered successfully!");
    }

    private boolean isValidRole(String role) {
        return role.equals("ADMIN") || role.equals("HR") || role.equals("USER");
    }
}
