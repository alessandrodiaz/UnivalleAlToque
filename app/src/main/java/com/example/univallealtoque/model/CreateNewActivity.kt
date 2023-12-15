package com.example.univallealtoque.model

data class CreateNewActivityResponseExpress(
    val message: String?,
    //val newDataPosted: newDataPosted?,
)

data class newDataPosted(
    val group_id : Int,
    val event_id : Int
)
