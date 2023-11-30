package com.example.univallealtoque.user_account

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.univallealtoque.model.DeleteAccountModel
import com.example.univallealtoque.network.AlToqueServiceFactory
import com.google.gson.GsonBuilder
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody

class DeleteAccountConfirmViewModel : ViewModel() {
    private val alToqueService = AlToqueServiceFactory.makeAlToqueService()

    private val _state = MutableStateFlow(DeleteAccountConfirmState())
    val state = _state.asStateFlow()


    fun deleteAccount(deleteAccountModel: DeleteAccountModel) {

        val gson = GsonBuilder().setPrettyPrinting().create()
        val json = gson.toJson(deleteAccountModel, DeleteAccountModel::class.java)
        val requestBody = json.toRequestBody("application/json".toMediaTypeOrNull())


        viewModelScope.launch {
            try {
                val response = alToqueService.deleteAccountConfirm(requestBody)

                println("RESPONSE"+response)

                if (response.message == "User successfully deactivated") {
                    _state.value =
                        DeleteAccountConfirmState(
                            isAccountDeleted = true,
                            isCodeValid = true,
                            isRequestSuccessful = true
                        )
                } else if (response.message == "Code expired") {
                    _state.value =
                        DeleteAccountConfirmState(
                            isAccountDeleted = false,
                            isCodeValid = false,
                            isRequestSuccessful = true
                        )
                }
            } catch (e: Exception) {
                Log.e(TAG, "Error al eliminar usuario", e)
                println("Error message: $e.error")

                _state.value =
                    DeleteAccountConfirmState(
                        isAccountDeleted = false,
                        isCodeValid = false,
                        isRequestSuccessful = true
                    )

            }
        }
    }

    fun resetState() {
        _state.update { DeleteAccountConfirmState() }
    }
}