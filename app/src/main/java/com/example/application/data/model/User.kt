package com.example.application.data.model

data class User (
    val username: String? = null,
    val email: String,
    val salt: String? = null,
    val password: String,
    val phone: String? = null,
    val dateOfBirth: String? = null,
    val photo: String? = null,
    val languageLevel: String? = null
    )