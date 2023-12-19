package com.example.univallealtoque.data

import android.content.Context;
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

public class StoreAppData(private val context: Context) {
    companion object {
        private val Context.dataStore: DataStore<Preferences> by preferencesDataStore("AppDataStore")
        val APP_DATA_KEY = stringPreferencesKey("app_data")
    }

    val getAppData: Flow<String?> = context.dataStore.data
        .map { preferences ->
            preferences[APP_DATA_KEY]
        }

    suspend fun saveAppData(data: String) {
        context.dataStore.edit { preferences ->
            preferences[APP_DATA_KEY] = data
        }
    }

    suspend fun updateAppData(data: String) {
        context.dataStore.edit { preferences ->
            preferences[APP_DATA_KEY] = data
        }
    }


}
