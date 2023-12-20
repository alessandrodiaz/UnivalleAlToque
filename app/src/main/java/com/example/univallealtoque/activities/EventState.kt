package com.example.univallealtoque.activities

import com.example.univallealtoque.model.eventList


class EventState (
    val isRequestSuccessful: Boolean = false,
    val name: String? = null,
    val event: List<eventList>?=null,
    val isEnrolled: Boolean = false
)