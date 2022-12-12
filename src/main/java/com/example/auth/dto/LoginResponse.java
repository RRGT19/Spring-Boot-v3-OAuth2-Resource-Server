package com.example.auth.dto;

public class LoginResponse {
    public final String accessToken;
    public final String tokenType = "Bearer";

    public LoginResponse(String accessToken) {
        this.accessToken = accessToken;
    }
}