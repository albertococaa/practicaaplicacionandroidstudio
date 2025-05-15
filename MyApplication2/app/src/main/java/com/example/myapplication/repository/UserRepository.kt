// app/src/main/java/com/example/myapplication/repository/UserRepository.kt
package com.example.myapplication.repository

import android.content.Context
import com.example.myapplication.data.local.AppDatabase
import com.example.myapplication.data.local.UserEntity

class UserRepository(context: Context) {
    private val userDao = AppDatabase.getInstance(context).userDao()

    /** Inserta un usuario nuevo (registro) */
    suspend fun register(user: UserEntity) {
        userDao.insert(user)
    }

    /** Devuelve el usuario si existe o null */
    suspend fun login(username: String, password: String): UserEntity? {
        return userDao.login(username, password)
    }
}
