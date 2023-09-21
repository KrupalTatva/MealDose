package com.app.compose_structure.data.local

interface ISharedPreferences {

    suspend fun storeValue(key: String, value: Any)

    suspend fun <T> getValue(key: String, default: T): T
    suspend fun clear()
}