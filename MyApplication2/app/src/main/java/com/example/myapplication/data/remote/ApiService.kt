// app/src/main/java/com/example/myapplication/data/remote/ApiService.kt
package com.example.myapplication.data.remote

import com.example.myapplication.model.ApiResponse
import retrofit2.http.GET

interface ApiService {
    @GET("api.json")
    suspend fun getAll(): ApiResponse
}
