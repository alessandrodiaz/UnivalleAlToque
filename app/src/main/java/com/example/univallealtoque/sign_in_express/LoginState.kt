package com.example.univallealtoque.sign_in_express

data class LoginState(
    val isLoginSuccessful: Boolean = false,
    val loginError: Boolean = false,
    val updateSuccessful: Boolean? = false,
)
