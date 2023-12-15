package com.example.univallealtoque.activities

import com.example.univallealtoque.model.ActivitiesList

class EnrolledActivitiesState(
    val isListObtained: Boolean = false,
    val isRequestSuccessful: Boolean = false,
    val activities: List<ActivitiesList>? = null,
)