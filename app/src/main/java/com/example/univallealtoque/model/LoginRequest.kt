package com.example.univallealtoque.model

import kotlinx.serialization.Serializable

@Serializable
public class LoginRequest(
    val email: String,
    val password: String
)