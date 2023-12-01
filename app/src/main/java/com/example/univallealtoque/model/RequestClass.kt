package com.example.univallealtoque.model

import kotlinx.serialization.Serializable

@Serializable
data class LoginRequestExpress(
    val email: String? = null,
    val password: String? = null
)

@Serializable
public class RecoverPasswordModel(
    val email: String,
)

@Serializable
public class LockoutModel(
    val email: String,
)

@Serializable
public class SendCodeDeleteAccountModel(
    val user_id: String,
    val password: String
)

@Serializable
public class DeleteAccountModel(
    val user_id: String,
    val code: String
)

@Serializable
public class RegisterModel(
    val name: String,
    val last_name: String,
    val email: String,
    val password: String
)