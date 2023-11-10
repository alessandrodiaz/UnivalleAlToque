package com.example.univallealtoque.model

data class UpdateBasicDataResponseExpress(
    val message: String?,
    val newDataPutted: newDataPutted?,
)

data class newDataPutted(
    val profile_photo: String? = null,
    val email: String? = null,
    val program: String? = null,
    val phone: String? = null
)