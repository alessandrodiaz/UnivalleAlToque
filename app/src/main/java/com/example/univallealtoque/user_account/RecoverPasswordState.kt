package com.example.univallealtoque.user_account

data class RecoverPasswordState (
    val isEmailSentSuccessfully: Boolean = false,
    val isEmailValid: Boolean = false,
    val isRequestSuccessful: Boolean = false,
    val randomCode: Int = -2,
)

