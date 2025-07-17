package com.example.employee.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import com.example.employee.dto.ChangePasswordRequest;
import com.example.employee.dto.UserProfileResponse;
import com.example.employee.entity.User;
import com.example.employee.repository.UserRepository;
import com.example.employee.util.PasswordValidator;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/user")
@CrossOrigin(origins = "*", maxAge = 3600)
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private PasswordValidator passwordValidator;

    @GetMapping("/profile")
    public ResponseEntity<?> getUserProfile() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        
        User user = userRepository.findByUsername(username).orElse(null);
        if (user == null) {
            return ResponseEntity.badRequest().body("User not found");
        }

        UserProfileResponse profile = new UserProfileResponse(
            user.getId(),
            user.getUsername(),
            user.getEmail(),
            user.getRole(),
            user.getLastLoginTime(),
            user.getPasswordChangedTime()
        );

        return ResponseEntity.ok(profile);
    }

    @PostMapping("/change-password")
    public ResponseEntity<?> changePassword(@RequestBody ChangePasswordRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        
        User user = userRepository.findByUsername(username).orElse(null);
        if (user == null) {
            return ResponseEntity.badRequest().body("User not found");
        }

        // Verify current password
        if (!passwordEncoder.matches(request.getCurrentPassword(), user.getPassword())) {
            return ResponseEntity.badRequest().body("Current password is incorrect");
        }

        // Validate new password strength
        if (!passwordValidator.isValid(request.getNewPassword())) {
            return ResponseEntity.badRequest().body(passwordValidator.getPasswordRequirements());
        }

        // Check if new password is different from current
        if (passwordEncoder.matches(request.getNewPassword(), user.getPassword())) {
            return ResponseEntity.badRequest().body("New password must be different from current password");
        }

        // Update password
        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        user.setPasswordChangedTime(LocalDateTime.now());
        userRepository.save(user);

        return ResponseEntity.ok("Password changed successfully!");
    }
}