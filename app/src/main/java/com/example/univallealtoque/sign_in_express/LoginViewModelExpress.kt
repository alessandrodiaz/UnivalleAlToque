package com.example.univallealtoque.sign_in_express

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.univallealtoque.model.LoginRequestExpress
import com.example.univallealtoque.model.LoginResponseExpress
import com.example.univallealtoque.model.UserData
import com.example.univallealtoque.network.AlToqueServiceFactory
import com.example.univallealtoque.sign_in_google.LoginState
import com.google.gson.GsonBuilder
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody

class LoginViewModelExpress : ViewModel() {
    // Expose screen UI state
    private val _privateLoginInputFromUser = MutableStateFlow(LoginRequestExpress())
    val loginInputFromUser: StateFlow<LoginRequestExpress> = _privateLoginInputFromUser.asStateFlow()
    private val _privateLoginResponseFromServer = MutableStateFlow(LoginResponseExpress())
    val loginResponseFromServer: StateFlow<LoginResponseExpress> = _privateLoginResponseFromServer.asStateFlow()
    private val _stateLoginExpress = MutableStateFlow(LoginState())
    val stateLoginExpress = _stateLoginExpress.asStateFlow()

    //Handle user input: email and password
    fun saveIntoViewModelUserInput(email: String, password: String){
        _privateLoginInputFromUser.update { currentState ->
            currentState.copy(
                email = email,
                password = password
            )
        }
        Log.d("UserLoginExpressUiState: ", loginInputFromUser.value.email.toString())
        Log.d("UserLoginExpressUiState: ", loginInputFromUser.value.password.toString())
    }

    fun loginUserWithExpress() {
        val alToqueService = AlToqueServiceFactory.makeAlToqueService()

        val gson = GsonBuilder().setPrettyPrinting().create()
        val json = gson.toJson(loginInputFromUser.value, LoginRequestExpress::class.java)
        val requestBody = json.toRequestBody("application/json".toMediaTypeOrNull())

        println("------>>>>>>$json")

        viewModelScope.launch {
            try {
                val response = alToqueService.loginUserExpress(requestBody)

                println("RESPONSE FROM EXPRESS " + response)
                fun updateLoginResponseFromServer(userData: UserData?,token: String?,message: String?){
                    _privateLoginResponseFromServer.update{ currentState ->
                        currentState.copy(
                            userData = userData,
                            token = token,
                            message = message
                        )
                    }
                }
                updateLoginResponseFromServer(response.userData,response.token,response.message)
                Log.d("Data from server: ", loginResponseFromServer.value.userData.toString())
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
                    //println("AAAAAAAAAAAAAAAAAAAAAAA")
                    _stateLoginExpress.value = LoginState(isLoginSuccessful = true, loginError = false)
                } else {
                    _stateLoginExpress.value = LoginState(isLoginSuccessful = false, loginError = true)
                }
            } catch (e: Exception) {
                _stateLoginExpress.value = LoginState(isLoginSuccessful = false, loginError = true)
                //println("Error al realizar la solicitud: ${e.message}")
            }
        }
    }
    fun resetLoginStateExpress () {
        _stateLoginExpress.update { LoginState() }
        _privateLoginInputFromUser.update { LoginRequestExpress() }
        _privateLoginResponseFromServer.update { LoginResponseExpress() }
    }
}