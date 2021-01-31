package com.example.quotes.data.db

import androidx.room.*
import com.example.quotes.domain.Quote
import io.reactivex.Completable
import io.reactivex.Single

@Dao
interface FavoriteQuoteDao {

    companion object {
        const val TABLE_NAME = "FAVORITE_QUOTES"
    }

    @Query("SELECT * FROM $TABLE_NAME ORDER BY user_order")
    fun getAll(): List<FavoriteQuoteDto>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addQuote(quote: FavoriteQuoteDto)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addQuotes(quotes: List<FavoriteQuoteDto>)

    @Query("SELECT user_order FROM $TABLE_NAME ORDER BY user_order DESC LIMIT 1")
    fun getNextOrderNumber(): Int

    @Update
    fun updateQuotes(quotes: List<FavoriteQuoteDto>)

    @Delete
    fun deleteQuote(quote: FavoriteQuoteDto)

    @Query("DELETE FROM $TABLE_NAME")
    fun clearTable()
}