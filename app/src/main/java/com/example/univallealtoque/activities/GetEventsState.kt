package com.example.univallealtoque.activities

import com.example.univallealtoque.model.EventsList

class GetEventsState(
    val isListObtained: Boolean = false,
    val isRequestSuccessful: Boolean = false,
    val events: List<EventsList>? = null,
)