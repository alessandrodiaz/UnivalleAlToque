package com.example.univallealtoque.sign_in_google

data class SignInResult(
    val data: UserDataGoogle?,
    val errorMessage: String?
)

data class UserDataGoogle(
    val userId: String,
    val username: String?,
    val profilePictureUrl: String?
)