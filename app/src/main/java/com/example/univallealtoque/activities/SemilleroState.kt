package com.example.univallealtoque.activities

import com.example.univallealtoque.model.semillerosList

class SemilleroState(
    val isRequestSuccessful: Boolean = false,
    val name: String? = null,
    val semilleros: List<semillerosList>?=null,
    val isEnrolled: Boolean = false
)
