package com.example.univallealtoque.model

import kotlinx.serialization.Serializable

@Serializable
public class RegisterModel(
    val name: String,
    val email: String,
    val password: String
)