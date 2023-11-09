package com.example.univallealtoque.sign_in_google

data class SignInState(
    val isSignInSuccessful: Boolean = false,
    val signInError: String? = null
)
