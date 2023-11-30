package com.example.univallealtoque.user_account

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.univallealtoque.model.RecoverPasswordModel
import com.example.univallealtoque.model.SendCodeDeleteAccountModel
import com.example.univallealtoque.network.AlToqueServiceFactory
import com.google.gson.GsonBuilder
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody

class SendCodeDeleteAccountViewModel : ViewModel() {
    private val alToqueService = AlToqueServiceFactory.makeAlToqueService()

    private val _state = MutableStateFlow(SendCodeDeleteAccountState())
    val state = _state.asStateFlow()


    fun sendCodeDeleteAccount(sendCodeDeleteAccountModel: SendCodeDeleteAccountModel) {

        val gson = GsonBuilder().setPrettyPrinting().create()
        val json = gson.toJson(sendCodeDeleteAccountModel, SendCodeDeleteAccountModel::class.java)
        val requestBody = json.toRequestBody("application/json".toMediaTypeOrNull())


        viewModelScope.launch {
            try {
                val response = alToqueService.sendCodeDeleteAccount(requestBody)

                if (response.message == "Code sent") {
                    _state.value =
                        SendCodeDeleteAccountState(
                            isEmailSentSuccessfully = true,
                            isPasswordValid = true,
                            isRequestSuccessful = true
                        )

                }
            } catch (e: Exception) {
                Log.e(TAG, "Error al recuperar la contraseña por correo", e)
                println("Error message: $e.error")

                _state.value =
                    SendCodeDeleteAccountState(
                        isEmailSentSuccessfully = false,
                        isPasswordValid = false,
                        isRequestSuccessful = true
                    )

            }
        }
    }

    fun resetState() {
        _state.update { SendCodeDeleteAccountState() }
    }
}