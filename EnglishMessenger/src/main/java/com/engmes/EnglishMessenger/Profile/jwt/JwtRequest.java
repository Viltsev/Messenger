package com.engmes.EnglishMessenger.Profile.jwt;

import lombok.Data;

@Data
public class JwtRequest {
    private String email;
    private String password;
}
