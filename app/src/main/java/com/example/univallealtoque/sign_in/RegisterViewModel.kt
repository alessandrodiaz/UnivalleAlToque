package com.example.univallealtoque.sign_in

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.univallealtoque.model.RegisterModel
import com.example.univallealtoque.network.AlToqueServiceFactory
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonObject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.json.JSONObject

import kotlinx.serialization.*
import kotlinx.serialization.json.*
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class RegisterViewModel : ViewModel() {

    private val _state = MutableStateFlow(SignInState())
    val state = _state.asStateFlow()

    fun registerUser(registerModel:RegisterModel) {
        val alToqueService = AlToqueServiceFactory.makeAlToqueService()

        val gson = GsonBuilder().setPrettyPrinting().create()
        val json = gson.toJson(registerModel, RegisterModel::class.java)
        val requestBody = json.toRequestBody("application/json".toMediaTypeOrNull())

        println("------>>>>>>$json")

        viewModelScope.launch {
            try {
                val response = alToqueService.registerUser(requestBody)

                println("MYRESPONSEEEEEEE"+response)

                // Handle the response.
                if (response == "OK") {
                    println("SUCESSFULLLLL"+response)
                    // The request was successful.
                } else {
                    println("NO SUCCESS"+response)
                }
//                if (response.isSuccessful) {
//                    val result = response.body() // Aquí debes definir la estructura del objeto de respuesta
////                    val registerModel = Gson().fromJson(responseBody, RegisterViewModel::class.java)
//                    // Actualiza el estado en consecuencia
//                    _state.value = SignInState(isSignInSuccessful = true, signInError = null)
//                } else {
//                    println("Error en la solicitud: ${response}")
//                }
            } catch (e: Exception) {
                 println("Error al realizar la solicitud: ${e.message}")
            }
        }
    }
}
