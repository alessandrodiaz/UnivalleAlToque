package com.example.univallealtoque.activities

import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.univallealtoque.model.SemillerosHomeList
import com.example.univallealtoque.network.AlToqueServiceFactory
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class GetSemillerosViewModel : ViewModel() {
    private val alToqueService = AlToqueServiceFactory.makeAlToqueService()

    private val _state = MutableStateFlow(GetSemillerosState())
    val state = _state.asStateFlow()

    private val _semilleros = MutableStateFlow<List<SemillerosHomeList>>(emptyList())
    val semilleros: StateFlow<List<SemillerosHomeList>> = _semilleros.asStateFlow()

    fun getSemilleros() {

        viewModelScope.launch {
            try {
                val response = alToqueService.getSemilleros()
                println("RESPONSE"+response)

                if (response.message == "sent") {

                    println("hola semilleros")

                    // Guardar las activities en el flujo _activities
                    _semilleros.value = response.activities

                    _state.value = GetSemillerosState(
                        isListObtained = true,
                        isRequestSuccessful = true,
                        semilleros = response.activities
                    )
                }
            } catch (e: Exception) {
                Log.e(ContentValues.TAG, "Error", e)
                println("Error message: $e.error")

                _state.value = GetSemillerosState(
                    isListObtained = false,
                    isRequestSuccessful = true,
                    semilleros = null
                )
            }
        }

        fun resetState() {
            _state.update { GetSemillerosState() }
        }
    }
}