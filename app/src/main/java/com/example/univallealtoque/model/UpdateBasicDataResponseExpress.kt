package com.example.univallealtoque.model

data class UpdateBasicDataResponseExpress(
    val message: String?,
    val newDataPutted: NewDataPutted?,
)

data class NewDataPutted(
    val profile_photo: String? = null,
    val program: String? = null,
    val phone: String? = null
)

// Función para almacenar datos
class DataStorage {
    private var storedData: NewDataPutted? = null

    fun storeData(data: NewDataPutted) {
        storedData = data
    }

    fun getData(): NewDataPutted? {
        return storedData
    }

    fun updateData(newData: NewDataPutted) {
        storedData = newData
    }
}