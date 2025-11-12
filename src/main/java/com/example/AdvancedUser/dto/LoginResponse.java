package com.example.AdvancedUser.dto;


public class LoginResponse {
    private String token;
    private String type = "Bearer";
    private String username;
    private String email;
    private Boolean isAdmin;

    public LoginResponse() {
    }

    public LoginResponse(String token, String username, String email, Boolean isAdmin) {
        this.token = token;
        this.username = username;
        this.email = email;
        this.isAdmin = isAdmin;
    }

    // Getters and Setters
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Boolean getIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(Boolean isAdmin) {
        this.isAdmin = isAdmin;
    }
}


