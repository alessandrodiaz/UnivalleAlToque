package com.example.univallealtoque.model

data class LoginResponseExpress(
    val userData: UserDataResponseExpress? = null,
    val token: String? = null,
    val message: String? = null
)

data class UserDataResponseExpress(
    val user_id: Int?,
    val name: String?,
    val last_name: String?,
    var profile_photo: String?,
    val email: String?,
    var program: String?,
    var phone: String?,
    val password: String?
)

data class RecoverPasswordResponse(
    val message: String? = null
)





