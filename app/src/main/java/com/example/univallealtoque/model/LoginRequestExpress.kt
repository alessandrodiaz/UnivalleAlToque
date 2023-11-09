package com.example.univallealtoque.model

import kotlinx.serialization.Serializable

@Serializable
data class LoginRequestExpress(
    val email: String? = null,
    val password: String? = null
)