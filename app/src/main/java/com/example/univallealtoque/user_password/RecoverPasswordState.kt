package com.example.univallealtoque.user_password

data class RecoverPasswordState (
    val isEmailSentSuccessfully: Boolean = false,
    val isEmailValid: Boolean = false,
    val isRequestSuccessful: Boolean = false
)