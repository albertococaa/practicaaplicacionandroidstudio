package com.example.myapplication.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface CartDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(item: CartItemEntity)

    @Delete
    suspend fun delete(item: CartItemEntity)

    @Query("SELECT * FROM cart_items")
    fun getAll(): Flow<List<CartItemEntity>>
}
