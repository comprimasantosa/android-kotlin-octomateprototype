package com.primasantosa.android.octomateprototype.util

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class PreferencesUtil(private val context: Context) {
    companion object {
        private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "octomatePrefs")
        private val nameKey = stringPreferencesKey("name")
        private val isLoginKey = booleanPreferencesKey("isLogin")
    }

    val getName: Flow<String>
        get() = context.dataStore.data.map {
            it[nameKey] ?: "Unknown"
        }

    val isLogin: Flow<Boolean>
        get() = context.dataStore.data.map {
            it[isLoginKey] ?: false
        }

    suspend fun setName(name: String) {
        context.dataStore.edit {
            it[nameKey] = name
        }
    }

    suspend fun setLogin(isLogin: Boolean) {
        context.dataStore.edit {
            it[isLoginKey] = isLogin
        }
    }
}