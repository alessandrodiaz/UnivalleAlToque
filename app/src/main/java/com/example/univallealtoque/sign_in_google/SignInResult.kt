package com.example.univallealtoque.sign_in_google

data class SignInResult(
    val data: UserData?,
    val errorMessage: String?
)

data class UserData(
    val userId:String,
    val username: String?,
    val profilePictureUrl: String?
)