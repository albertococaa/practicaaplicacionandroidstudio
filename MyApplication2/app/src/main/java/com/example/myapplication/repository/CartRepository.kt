package com.example.myapplication.repository

import android.content.Context
import com.example.myapplication.data.local.AppDatabase
import com.example.myapplication.data.local.CartItemEntity
import kotlinx.coroutines.flow.Flow

class CartRepository(context: Context) {
    private val dao = AppDatabase.getInstance(context).cartDao()

    suspend fun add(item: CartItemEntity) = dao.insert(item)
    suspend fun remove(item: CartItemEntity) = dao.delete(item)
    fun all(): Flow<List<CartItemEntity>> = dao.getAll()
}
