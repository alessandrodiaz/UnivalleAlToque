package com.example.univallealtoque.activities

import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.univallealtoque.model.ActivitiesList
import com.example.univallealtoque.model.SemilleroModel
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

    private val _activities = MutableStateFlow<List<ActivitiesList>>(emptyList())
    val activities: StateFlow<List<ActivitiesList>> = _activities.asStateFlow()

    fun semilleroInfo(semillerosModel: SemilleroModel) {

        val gson = GsonBuilder().setPrettyPrinting().create()
        val json = gson.toJson(semillerosModel, SemilleroModel::class.java)
        val requestBody = json.toRequestBody("application/json".toMediaTypeOrNull())

        viewModelScope.launch {
            try {
                val response = alToqueService.semilleroInfo(requestBody)
                println("RESPONSE"+response)

                if (response.message == "Semillero sent") {
                    _state.value =
                        SemillerosSatate(
                            isRequestSuccessful = true,
                            name = response.group_name,
                            description = response.group_description,
                            slots = response.slots,
                            available_slots = response.available_slots,
                            monday_start = response.monday_start,
                            monday_end = response.monday_end,
                            tuesday_start = response.tuesday_start,
                            tuesday_end = response.tuesday_end,
                            wednesday_start = response.wednesday_start,
                            wednesday_end= response.wednesday_end,
                            thursday_start = response.thursday_start,
                            thursday_end = response.thursday_end,
                            friday_start = response.friday_start,
                            friday_end = response.friday_end,
                            saturday_start = response.saturday_start,
                            saturday_end= response.saturday_end,
                            place = response.place,
                            isEnrolled = false
                        )
                }
            } catch (e: Exception) {
                Log.e(ContentValues.TAG, "Error", e)
                println("Error message: $e.error")

            }
        }
    }

    fun resetState() {
        _state.update { SemillerosSatate() }
    }

}