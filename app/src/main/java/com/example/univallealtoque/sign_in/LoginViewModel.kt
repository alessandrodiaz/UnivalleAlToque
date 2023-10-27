package com.example.univallealtoque.sign_in

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.univallealtoque.model.LoginRequest
import com.example.univallealtoque.model.RegisterModel
import com.example.univallealtoque.model.UserData
import com.example.univallealtoque.network.AlToqueServiceFactory
import com.google.firebase.firestore.auth.User
import com.google.gson.GsonBuilder
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody

class LoginViewModel() : ViewModel() {

    private val _state = MutableStateFlow(LoginState())
    val state = _state.asStateFlow()

    fun loginUser(loginModel: LoginRequest) {
        val alToqueService = AlToqueServiceFactory.makeAlToqueService()

        val gson = GsonBuilder().setPrettyPrinting().create()
        val json = gson.toJson(loginModel, LoginRequest::class.java)
        val requestBody = json.toRequestBody("application/json".toMediaTypeOrNull())

        println("------>>>>>>$json")

        viewModelScope.launch {
            try {
                val response = alToqueService.loginUser(requestBody)
                println("RESPONSE" + response.message)

                /*EJEMPLO DE RESPONSE
                {
                    "userData": {
                    "user_id": 39,
                    "name": "claudia",
                    "last_name": null,
                    "profile_photo": null,
                    "email": "claudia@gmail.com",
                    "program": null,
                    "phone": null,
                    "password": "$2a$10$59CuBVNoLRnZ8zL9XBh2N.bRUXMzruOZ8lTsPkf2k9nksioLc/.Aq"
                },
                    "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyX2lkIjozOSwiZW1haWwiOiJjbGF1ZGlhQGdtYWlsLmNvbSIsImlhdCI6MTY5ODQyMTU1Mn0.t_cZumIRq4MvRlO5YoSR9uRxi1VejWkT4Fumpe-FIyA",
                    "message": "Inicio de sesión exitoso"
                }*/

                if (response.message == "Inicio de sesión exitoso") {
                    println("AAAAAAAAAAAAAAAAAAAAAAA")
                    _state.value = LoginState(isLoginSuccessful = true, loginError = false)
                } else {
                    _state.value = LoginState(isLoginSuccessful = false, loginError = true)
                }
            } catch (e: Exception) {
                _state.value = LoginState(isLoginSuccessful = false, loginError = true)
                println("Error al realizar la solicitud: ${e.message}")
            }
        }
    }

    fun resetState() {
        _state.update { LoginState() }
    }
}