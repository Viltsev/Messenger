package com.engmes.EnglishMessenger.Profile.model;

import jakarta.persistence.Entity;
import lombok.Data;

import java.util.Date;

@Data
public class OnboardingInfo {
    private String email;
    private String username;
    private Date dateOfBirth;
    private String photo;
}
