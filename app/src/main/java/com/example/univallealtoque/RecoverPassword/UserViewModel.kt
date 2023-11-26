package com.example.univallealtoque.RecoverPassword

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.univallealtoque.model.UserDataResponse
import com.example.univallealtoque.network.AlToqueServiceFactory
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class UserViewModel : ViewModel() {
    private val alToqueService = AlToqueServiceFactory.makeAlToqueService()

    private val _recoveryMessage = MutableStateFlow<String>("")
    val recoveryMessage: StateFlow<String> = _recoveryMessage

    fun getUserByEmail(email: String) {
        viewModelScope.launch {
            try {
                val message = alToqueService.getUserByEmail(email)
                _recoveryMessage.value = message.toString()
            } catch (e: Exception) {
                Log.e(TAG, "Error al recuperar la contraseña por correo", e)
                _recoveryMessage.value = "El correo no se encuentra registrado"
            }
        }
    }

    companion object {
        private const val TAG = "UserViewModel"
    }
}
