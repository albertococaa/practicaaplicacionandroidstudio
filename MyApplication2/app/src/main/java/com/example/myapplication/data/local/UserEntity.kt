// app/src/main/java/com/example/myapplication/data/local/UserEntity.kt
package com.example.myapplication.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class UserEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,            // ← ahora sí existe el parámetro `id`
    val username: String,
    val password: String
)
