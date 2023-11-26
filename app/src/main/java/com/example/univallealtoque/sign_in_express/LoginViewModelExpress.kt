package com.example.univallealtoque.sign_in_express

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.univallealtoque.data.DataStoreSingleton
import com.example.univallealtoque.model.LoginRequestExpress
import com.example.univallealtoque.model.LoginResponseExpress
import com.example.univallealtoque.model.UserDataExpress
import com.example.univallealtoque.network.AlToqueServiceFactory
import com.example.univallealtoque.sign_in_google.LoginState
import com.google.gson.GsonBuilder
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody


class LoginViewModelExpress() : ViewModel() {
    // Expose screen UI state
    private val _privateLoginInputFromUser = MutableStateFlow(LoginRequestExpress())
    val loginInputFromUser: StateFlow<LoginRequestExpress> =
        _privateLoginInputFromUser.asStateFlow()
    private val _privateLoginOrUpdateResponseFromServer = MutableStateFlow(LoginResponseExpress())
    val loginOrUpdateResponseFromServer: StateFlow<LoginResponseExpress> =
        _privateLoginOrUpdateResponseFromServer.asStateFlow()
    private val _stateLoginExpress = MutableStateFlow(LoginState())
    val stateLoginExpress = _stateLoginExpress.asStateFlow()


    //Handle user input: email and password
    fun saveIntoViewModelUserInput(email: String, password: String) {
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
        Log.d("request", "$requestBody")

        viewModelScope.launch {
            try {
                val response = alToqueService.loginUserExpress(requestBody)

                println("RESPONSE FROM EXPRESS " + response)
                fun updateStatesAfterLoginResponseFromServer(
                    userDataExpress: UserDataExpress?,
                    token: String?,
                    message: String?
                ) {
                    _privateLoginOrUpdateResponseFromServer.update { currentState ->
                        currentState.copy(
                            userData = userDataExpress,
                            token = token,
                            message = message
                        )
                    }
                }
                updateStatesAfterLoginResponseFromServer(
                    response.userData,
                    response.token,
                    response.message
                )
                Log.d(
                    "Data from server: ",
                    loginOrUpdateResponseFromServer.value.userData.toString()
                )
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
                    // Ejemplo desde un Fragment
                    val userData = response.userData ?: UserDataExpress(
                        user_id = null,
                        name = null,
                        last_name = null,
                        profile_photo = null,
                        email = null,
                        program = null,
                        phone = null,
                        password = null
                    )
                    DataStoreSingleton.saveUserData(userData)


                    // Recolectar el valor del flujo getEmail
                    val data: Flow<UserDataExpress?> = DataStoreSingleton.getUserData()
                    println("Valor del email recolectado: $data")

                    _stateLoginExpress.value =
                        LoginState(isLoginSuccessful = true, loginError = false)
                } else {
                    _stateLoginExpress.value =
                        LoginState(isLoginSuccessful = false, loginError = true)
                }
            } catch (e: Exception) {
                _stateLoginExpress.value = LoginState(isLoginSuccessful = false, loginError = true)
                //println("Error al realizar la solicitud: ${e.message}")
            }
        }
    }

    fun updateBasicData(
        newProfilePhoto: String? = null,
        newProgram: String? = null,
        newPhone: String? = null
    ) {

        viewModelScope.launch {

            if (newPhone != null) {
                DataStoreSingleton.updatePhone(newPhone)
            }
            if (newProgram != null) {
                DataStoreSingleton.updateProgram(newProgram)
            }
            if (newProfilePhoto != null) {
                DataStoreSingleton.updateProfilePhoto(newProfilePhoto)
            }

            val userData = DataStoreSingleton.getUserData().first()

            var jsonUserData: String = ""

            userData?.let {
                val userDataMap = mapOf(
                    "user_id" to userData.user_id,
                    "program" to userData.program,
                    "phone" to userData.phone,
                    "profile_photo" to userData.profile_photo
                )

                val gson = GsonBuilder().setPrettyPrinting().create()

                jsonUserData = gson.toJson(userDataMap)

                println(jsonUserData)
            }

            val alToqueService = AlToqueServiceFactory.makeAlToqueService()

            val requestBody = jsonUserData.toRequestBody("application/json".toMediaTypeOrNull())
            println("------>>>>>>$jsonUserData")

            try {
                val response = alToqueService.updateProfile(requestBody)

                println("RESPONSE FROM EXPRESS " + response)
            } catch (e: Exception) {
                println("Error al realizar la actualizacion: ${e.message}")
            }
        }
    }


    fun resetLoginStateExpress() {
        _stateLoginExpress.update { LoginState() }
        _privateLoginInputFromUser.update { LoginRequestExpress() }
        _privateLoginOrUpdateResponseFromServer.update { LoginResponseExpress() }
    }
}