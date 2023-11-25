package com.example.univallealtoque.data

import android.content.Context;
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.univallealtoque.model.UserDataExpress
import com.google.firebase.firestore.UserDataReader
import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

public class StoreUserData(private val context: Context) {
    companion object {
        private val Context.dataStore: DataStore<Preferences> by preferencesDataStore("UserDataStore")
        val USER_DATA_KEY = stringPreferencesKey("user_data")
    }

    val getUserData: Flow<UserDataExpress?> = context.dataStore.data
        .map { preferences ->
            val userDataString = preferences[USER_DATA_KEY]
            Gson().fromJson(userDataString, UserDataExpress::class.java)
        }

    suspend fun saveUserData(userData: UserDataExpress) {
        val userDataString = Gson().toJson(userData)
        context.dataStore.edit { preferences ->
            preferences[USER_DATA_KEY] = userDataString
        }
    }

    suspend fun updatePhone(newPhone: String) {
        context.dataStore.edit { preferences ->
            val userDataString = preferences[USER_DATA_KEY]
            val userData = Gson().fromJson(userDataString, UserDataExpress::class.java)

            userData?.let {
                it.phone = newPhone
                val updatedUserDataString = Gson().toJson(it)
                preferences[USER_DATA_KEY] = updatedUserDataString
            }
        }
    }

    suspend fun updateProgram(newProgram: String) {
        context.dataStore.edit { preferences ->
            val userDataString = preferences[USER_DATA_KEY]
            val userData = Gson().fromJson(userDataString, UserDataExpress::class.java)

            userData?.let {
                it.program = newProgram
                val updatedUserDataString = Gson().toJson(it)
                preferences[USER_DATA_KEY] = updatedUserDataString
            }
        }
    }

    suspend fun updateProfilePhoto(newProfilePhoto: String) {
        context.dataStore.edit { preferences ->
            val userDataString = preferences[USER_DATA_KEY]
            val userData = Gson().fromJson(userDataString, UserDataExpress::class.java)

            userData?.let {
                it.profile_photo = newProfilePhoto
                val updatedUserDataString = Gson().toJson(it)
                preferences[USER_DATA_KEY] = updatedUserDataString
            }
        }
    }

}

