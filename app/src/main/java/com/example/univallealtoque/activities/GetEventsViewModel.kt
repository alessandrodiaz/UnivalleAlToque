package com.example.univallealtoque.activities

import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.univallealtoque.model.ActivitiesList
import com.example.univallealtoque.model.EnrolledActivitiesModel
import com.example.univallealtoque.model.EventsList
import com.example.univallealtoque.network.AlToqueServiceFactory
import com.google.gson.GsonBuilder
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody

class GetEventsViewModel : ViewModel() {
    private val alToqueService = AlToqueServiceFactory.makeAlToqueService()

    private val _state = MutableStateFlow(GetEventsState())
    val state = _state.asStateFlow()

    private val _events = MutableStateFlow<List<EventsList>>(emptyList())
    val events: StateFlow<List<EventsList>> = _events.asStateFlow()

    fun getEvents() {
        viewModelScope.launch {
            try {
                val response = alToqueService.getEvents()
                println("RESPONSE"+response)

                if (response.message == "Events sent") {

                    // Guardar las activities en el flujo _activities
                    _events.value = response.events

                    _state.value = GetEventsState(
                        isListObtained = true,
                        isRequestSuccessful = true,
                        events = response.events
                    )
                }
            } catch (e: Exception) {
                Log.e(ContentValues.TAG, "Error", e)
                println("Error message: $e.error")


                    _state.value = GetEventsState(
                    isListObtained = false,
                    isRequestSuccessful = true,
                    events = null
                )
            }
        }
    }

    fun resetState() {
        _state.update { GetEventsState() }
    }
}