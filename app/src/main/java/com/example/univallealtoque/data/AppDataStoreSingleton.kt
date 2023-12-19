package com.example.univallealtoque.data

import android.content.Context
import kotlinx.coroutines.flow.Flow

object AppDataStoreSingleton {
    private lateinit var storeAppData: StoreAppData

    fun initialize(context: Context) {
        storeAppData = StoreAppData(context)
    }

    suspend fun saveAppData(data: String) {
        storeAppData.saveAppData(data)
    }

    fun getAppData(): Flow<String?> {
        return storeAppData.getAppData
    }

    suspend fun updateAppData(data: String) {
        storeAppData.updateAppData(data)
    }

}