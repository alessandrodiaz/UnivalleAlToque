package com.example.univallealtoque.user_account

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.univallealtoque.model.RecoverPasswordModel
import com.example.univallealtoque.network.AlToqueServiceFactory
import com.google.gson.GsonBuilder
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody

class RecoverPasswordViewModel : ViewModel() {
    private val alToqueService = AlToqueServiceFactory.makeAlToqueService()

    private val _recoveryMessage = MutableStateFlow<String>("")
    val recoveryMessage: StateFlow<String> = _recoveryMessage

    private val _state = MutableStateFlow(RecoverPasswordState())
    val state = _state.asStateFlow()

    fun recoverPassword(recoverPasswordModel: RecoverPasswordModel) {

        val gson = GsonBuilder().setPrettyPrinting().create()
        val json = gson.toJson(recoverPasswordModel, RecoverPasswordModel::class.java)
        val requestBody = json.toRequestBody("application/json".toMediaTypeOrNull())

        viewModelScope.launch {
            try {
                val response = alToqueService.recoverPassword(requestBody)

                println("----->RESPONSE RECOVER" + response)
                if (response.message == "Email sent successfully") {
                    _state.value =
                        RecoverPasswordState(
                            isEmailSentSuccessfully = true,
                            isEmailValid = true,
                            isRequestSuccessful = true,
                            randomCode = response.randomCode,
                            expirationDate = response.expirationDateString,
                        )
                }
            } catch (e: Exception) {
                Log.e(TAG, "Error al recuperar la contraseña por correo", e)
                println("Error message: $e")

                _state.value =
                    RecoverPasswordState(
                        isEmailSentSuccessfully = false,
                        isEmailValid = false,
                        isRequestSuccessful = true,
                        randomCode = -1,
                        expirationDate = "",
                    )
            }
        }
    }

    companion object {
        private const val TAG = "UserViewModel"
    }

    fun resetState() {
        _state.update { RecoverPasswordState() }
    }
}
