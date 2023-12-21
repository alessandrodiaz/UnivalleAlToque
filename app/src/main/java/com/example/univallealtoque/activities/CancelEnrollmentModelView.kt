package com.example.univallealtoque.activities

import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.univallealtoque.model.ActivitiesList
import com.example.univallealtoque.model.CancelEnrollmentModel
import com.example.univallealtoque.model.EnrolledActivitiesModel
import com.example.univallealtoque.model.EnrollmentModel
import com.example.univallealtoque.network.AlToqueServiceFactory
import com.google.gson.GsonBuilder
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody

class CancelEnrollmentModelView : ViewModel() {
    private val alToqueService = AlToqueServiceFactory.makeAlToqueService()

    private val _state = MutableStateFlow(CancelEnrollmentState())
    val state = _state.asStateFlow()

    private val _recoveryMessage = MutableStateFlow<String>("")
    val recoveryMessage: StateFlow<String> = _recoveryMessage

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    fun cancelEnrollment(cancelEnrollmentModel: CancelEnrollmentModel) {

        val gson = GsonBuilder().setPrettyPrinting().create()
        val json = gson.toJson(cancelEnrollmentModel, CancelEnrollmentModel::class.java)
        val requestBody = json.toRequestBody("application/json".toMediaTypeOrNull())

        viewModelScope.launch {
            try {
                _isLoading.value = true

                val response = alToqueService.cancelEnrrollment(requestBody)
                println("RESPONSE"+response)

                if (response.message == "Enrollment canceled successfully") {

                    // Guardar las activities en el flujo _activities

                    _state.value = CancelEnrollmentState(
                        isRequestSuccessful = true
                    )
                }
            } catch (e: Exception) {
                Log.e(ContentValues.TAG, "Error", e)
                println("Error message: $e.error")

                _state.value = CancelEnrollmentState(
                    isRequestSuccessful = false
                )

            } finally {
                _isLoading.value = false
            }
        }
    }

    fun resetState() {
        _state.update { CancelEnrollmentState() }
    }
}