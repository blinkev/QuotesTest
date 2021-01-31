package com.example.quotes.data.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [FavoriteQuoteDto::class], version = 1)
abstract class Database : RoomDatabase() {
    abstract fun favoriteQuoteDao(): FavoriteQuoteDao
}