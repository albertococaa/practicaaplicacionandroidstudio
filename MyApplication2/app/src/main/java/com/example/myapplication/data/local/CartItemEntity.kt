
package com.example.myapplication.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cart_items")
data class CartItemEntity(
    @PrimaryKey(autoGenerate = true)
    val cartItemId: Long = 0L,
    val jerseyId: Int,
    val team: String,
    val price: Double,
    val imageUrl: String
)
