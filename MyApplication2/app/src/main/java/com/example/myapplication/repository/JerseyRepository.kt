package com.example.myapplication.repository

import com.example.myapplication.data.remote.RetrofitClient
import com.example.myapplication.model.Jersey

class JerseyRepository {
    suspend fun fetchAll(): List<Jersey> {
        val response = RetrofitClient.apiService.getAll()
        return response.jerseys
    }
}
