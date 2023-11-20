package com.example.univallealtoque.data

import android.content.Context
import kotlinx.coroutines.flow.Flow

object DataStoreSingleton {
    private lateinit var storeUserData: StoreUserData

    fun initialize(context: Context) {
        storeUserData = StoreUserData(context)
    }

    suspend fun saveEmail(email: String) {
        storeUserData.saveEmail(email)
    }

    fun getEmail(): Flow<String?> {
        return storeUserData.getEmail
    }
}
