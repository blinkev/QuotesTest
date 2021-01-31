package com.example.quotes.data.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = FavoriteQuoteDao.TABLE_NAME)
data class FavoriteQuoteDto(
    @PrimaryKey
    val name: String,
    val bid: Double,
    val ask: Double,
    val spread: Float,
    @ColumnInfo(name = "user_order")
    val userOrder: Int
)