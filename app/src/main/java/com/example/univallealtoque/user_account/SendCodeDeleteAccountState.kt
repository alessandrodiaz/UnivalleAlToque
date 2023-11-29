package com.example.univallealtoque.user_account

data class SendCodeDeleteAccountState (
    val isEmailSentSuccessfully: Boolean = false,
    val isPasswordValid: Boolean = false,
    val isRequestSuccessful: Boolean = false
)