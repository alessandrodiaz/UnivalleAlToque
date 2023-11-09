package com.example.univallealtoque.sign_in_google

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.univallealtoque.model.RegisterModel
import com.example.univallealtoque.network.AlToqueServiceFactory
import com.google.gson.GsonBuilder
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody


class RegisterViewModel() : ViewModel() {

    private val _state = MutableStateFlow(RegisterState())
    val state = _state.asStateFlow()

    fun registerUser(registerModel:RegisterModel) {
        val alToqueService = AlToqueServiceFactory.makeAlToqueService()

        val gson = GsonBuilder().setPrettyPrinting().create()
        val json = gson.toJson(registerModel, RegisterModel::class.java)
        val requestBody = json.toRequestBody("application/json".toMediaTypeOrNull())

//        println("------>>>>>>$json")

        viewModelScope.launch {
            try {
                val response = alToqueService.registerUser(requestBody)

                if (response == "OK") {
                    _state.value = RegisterState(isRegisterSuccessful = true, registerError = false)
                } else {
                    _state.value = RegisterState(isRegisterSuccessful = false, registerError = true)
                }
            } catch (e: Exception) {
                _state.value = RegisterState(isRegisterSuccessful = false, registerError = true)
                 println("Error al realizar la solicitud: ${e.message}")
            }
        }
    }

    fun resetState() {
        _state.update { RegisterState() }
    }
}
