package com.example.univallealtoque.user_account

import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.univallealtoque.model.DeleteAccountModel
import com.example.univallealtoque.model.NewPasswordModel
import com.example.univallealtoque.network.AlToqueServiceFactory
import com.google.gson.GsonBuilder
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody

class NewPasswordViewModel : ViewModel() {
    private val alToqueService = AlToqueServiceFactory.makeAlToqueService()

    private val _state = MutableStateFlow(NewPasswordState())
    val state = _state.asStateFlow()


    fun newPassword(newPasswordModel: NewPasswordModel) {

        val gson = GsonBuilder().setPrettyPrinting().create()
        val json = gson.toJson(newPasswordModel, NewPasswordModel::class.java)
        val requestBody = json.toRequestBody("application/json".toMediaTypeOrNull())


        viewModelScope.launch {
            try {
                val response = alToqueService.newPassword(requestBody)

                println("RESPONSE"+response)

                if (response.message == "Password updated successfully") {
                    _state.value =
                        NewPasswordState(
                            isPasswordUpdated = true,
                            isRequestSuccessful = true
                        )
                }
            } catch (e: Exception) {
                Log.e(ContentValues.TAG, "Error", e)
                println("Error message: $e.error")

                _state.value =
                    NewPasswordState(
                        isPasswordUpdated = false,
                        isRequestSuccessful = true
                    )

            }
        }
    }

    fun resetState() {
        _state.update { NewPasswordState() }
    }
}