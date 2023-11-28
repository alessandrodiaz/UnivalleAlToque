package com.example.univallealtoque.model

data class UpdateBasicDataResponseExpress(
    val message: String?,
    val newDataPutted: NewDataPutted?,
)

data class NewDataPutted(
    val profile_photo: String? = null,
    val program: String? = null,
    val phone: String? = null
)
