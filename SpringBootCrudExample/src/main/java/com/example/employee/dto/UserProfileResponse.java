package com.example.employee.dto;

import java.time.LocalDateTime;

public class UserProfileResponse {
    private String id;
    private String username;
    private String email;
    private String role;
    private LocalDateTime lastLoginTime;
    private LocalDateTime passwordChangedTime;

    public UserProfileResponse(String id, String username, String email, String role, 
                             LocalDateTime lastLoginTime, LocalDateTime passwordChangedTime) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.role = role;
        this.lastLoginTime = lastLoginTime;
        this.passwordChangedTime = passwordChangedTime;
    }

    // Getters and Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }
    public LocalDateTime getLastLoginTime() { return lastLoginTime; }
    public void setLastLoginTime(LocalDateTime lastLoginTime) { this.lastLoginTime = lastLoginTime; }
    public LocalDateTime getPasswordChangedTime() { return passwordChangedTime; }
    public void setPasswordChangedTime(LocalDateTime passwordChangedTime) { this.passwordChangedTime = passwordChangedTime; }
}