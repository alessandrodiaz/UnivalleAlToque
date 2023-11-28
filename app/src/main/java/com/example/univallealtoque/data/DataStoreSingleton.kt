package com.example.univallealtoque.data

import android.content.Context
import com.example.univallealtoque.model.UserDataResponseExpress
import kotlinx.coroutines.flow.Flow

object DataStoreSingleton {
    private lateinit var storeUserData: StoreUserData

    fun initialize(context: Context) {
        storeUserData = StoreUserData(context)
    }

    suspend fun saveUserData(userData: UserDataResponseExpress) {
        storeUserData.saveUserData(userData)
    }

    fun getUserData(): Flow<UserDataResponseExpress?> {
        return storeUserData.getUserData
    }

    suspend fun updatePhone(newPhone: String) {
        storeUserData.updatePhone(newPhone)
    }

    suspend fun updateProgram(newProgram: String) {
        storeUserData.updateProgram(newProgram)
    }

    suspend fun updateProfilePhoto(newProfilePhoto: String) {
        storeUserData.updateProfilePhoto(newProfilePhoto)
    }
}

