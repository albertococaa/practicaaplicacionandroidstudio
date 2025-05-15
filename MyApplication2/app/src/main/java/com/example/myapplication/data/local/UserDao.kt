package com.example.myapplication.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface UserDao {
    @Insert
    suspend fun insert(user: UserEntity)

    /** Devuelve el usuario si el usuario+password coinciden, o null si no existe */
    @Query("SELECT * FROM users WHERE username = :username AND password = :password")
    suspend fun login(username: String, password: String): UserEntity?
}
