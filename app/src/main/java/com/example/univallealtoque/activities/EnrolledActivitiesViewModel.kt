package com.example.univallealtoque.activities

import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.univallealtoque.model.ActivitiesList
import com.example.univallealtoque.model.EnrolledActivitiesModel
import com.example.univallealtoque.network.AlToqueServiceFactory
import com.google.gson.GsonBuilder
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody

class EnrolledActivitiesViewModel : ViewModel() {
    private val alToqueService = AlToqueServiceFactory.makeAlToqueService()

    private val _state = MutableStateFlow(EnrolledActivitiesState())
    val state = _state.asStateFlow()

    private val _activities = MutableStateFlow<List<ActivitiesList>>(emptyList())
    val activities: StateFlow<List<ActivitiesList>> = _activities.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()


    fun enrolledActivities(enrolledActivitiesModel: EnrolledActivitiesModel) {

        val gson = GsonBuilder().setPrettyPrinting().create()
        val json = gson.toJson(enrolledActivitiesModel, EnrolledActivitiesModel::class.java)
        val requestBody = json.toRequestBody("application/json".toMediaTypeOrNull())


        viewModelScope.launch {
            try {
                _isLoading.value = true

                val response = alToqueService.enrolledActivities(requestBody)
                println("RESPONSE"+response)

                if (response.message == "Activities sent") {

                    for (activity in response.activities) {
                        // Accede a las propiedades del elemento actual (e.g., activity.groupName)
                        println(activity.event_photo)
                    }

                    // Guardar las activities en el flujo _activities
                    _activities.value = response.activities

                    _state.value = EnrolledActivitiesState(
                        isListObtained = true,
                        isRequestSuccessful = true,
                        activities = response.activities
                    )
                }
            } catch (e: Exception) {
                Log.e(ContentValues.TAG, "Error", e)
                println("Error message: $e.error")

                _state.value =
                    EnrolledActivitiesState(
                        isListObtained = false,
                        isRequestSuccessful = true,
                        activities = null
                    )

            } finally {
                _isLoading.value = false
            }
        }
    }

    fun resetState() {
        _state.update { EnrolledActivitiesState() }
    }
}