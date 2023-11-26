package com.example.univallealtoque.user_password

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.univallealtoque.model.RecoverPasswordModel
import com.example.univallealtoque.network.AlToqueServiceFactory
import com.google.gson.GsonBuilder
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody

class UserPasswordModel : ViewModel() {
    private val alToqueService = AlToqueServiceFactory.makeAlToqueService()

    private val _recoveryMessage = MutableStateFlow<String>("")
    val recoveryMessage: StateFlow<String> = _recoveryMessage

    fun recoverPassword(recoverPasswordModel: RecoverPasswordModel) {

        val gson = GsonBuilder().setPrettyPrinting().create()
        val json = gson.toJson(recoverPasswordModel, RecoverPasswordModel::class.java)
        val requestBody = json.toRequestBody("application/json".toMediaTypeOrNull())


        viewModelScope.launch {
            try {
                val message = alToqueService.recoverPassword(requestBody)

                println("----->RESPONSE"+message)

            } catch (e: Exception) {
                Log.e(TAG, "Error al recuperar la contraseña por correo", e)
            }
        }
    }

    companion object {
        private const val TAG = "UserViewModel"
    }
}
