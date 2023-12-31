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
public class ChangePasswordModel(
    val user_id: String,
    val old_password: String,
    val new_password: String
)

@Serializable
public class RegisterModel(
    val name: String,
    val last_name: String,
    val email: String,
    val password: String
)

@Serializable
public class NewPasswordModel(
    val email: String,
    val new_password: String
)

@Serializable
public class EnrolledActivitiesModel(
    val user_id: String,
)

@Serializable
public class SemilleroModel(
    val user_id: String,
    val semillero_id: String
)

@Serializable
public class EventModel(
    val user_id: String,
    val event_id: String
)

@Serializable
public class EnrollmentModel(
    val user_id: String,
    val activity_id: String,
    val activity_type: String
)

@Serializable
public class CancelEnrollmentModel(
    val user_id: String,
    val activity_id: String,
    val activity_type: String
)