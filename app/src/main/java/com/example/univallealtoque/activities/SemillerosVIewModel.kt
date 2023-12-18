package com.example.univallealtoque.activities

import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.univallealtoque.model.ActivitiesList
import com.example.univallealtoque.model.SemilleroModel
import com.example.univallealtoque.model.semillerosList
import com.example.univallealtoque.network.AlToqueServiceFactory
import com.example.univallealtoque.user_account.LockoutUserState
import com.google.gson.GsonBuilder
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody

class SemillerosVIewModel:ViewModel() {
    private val alToqueService = AlToqueServiceFactory.makeAlToqueService()

    private val _state = MutableStateFlow(SemillerosSatate())
    val state = _state.asStateFlow()

    private val _activities = MutableStateFlow(semillerosList())
    val activities: StateFlow<semillerosList> = _activities.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    fun semilleroInfo(semillerosModel: SemilleroModel) {

        val gson = GsonBuilder().setPrettyPrinting().create()
        val json = gson.toJson(semillerosModel, SemilleroModel::class.java)
        val requestBody = json.toRequestBody("application/json".toMediaTypeOrNull())

        viewModelScope.launch {
            try {

                _isLoading.value = true
                val response = alToqueService.semilleroInfo(requestBody)
                println("RESPONSE"+response)

                if (response.message == "Semillero Sent") {
                    _activities.value = response.semilleroInfoArray.firstOrNull()!!

                    println("FINAL " +_activities)
                    println("OBTENIDO "+ response.semilleroInfoArray)
                    Log.d("OBTENIDO DESDE EL SERVER: ", response.semilleroInfoArray.toString())

                    _state.value =
                        SemillerosSatate(
                            semilleros = response.semilleroInfoArray,
                            isRequestSuccessful = true,
                            isEnrolled = response.isUserEnrolled

                        )
                }
            } catch (e: Exception) {
                Log.e(ContentValues.TAG, "Error", e)
                println("Error message: $e.error")

            }finally {
                _isLoading.value = false
            }
        }
    }

    fun resetState() {
        _state.update { SemillerosSatate() }
    }
}