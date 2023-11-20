package com.example.univallealtoque.model

data class LoginResponseExpress(
    val userData: UserDataExpress? = null,
    val token: String? = null,
    val message: String? = null
)

data class UserDataExpress(
    val user_id: Int?,
    val name: String?,
    val last_name: String?,
    val profile_photo: String?,
    val email: String?,
    val program: String?,
    val phone: String?,
    val password: String?
)



