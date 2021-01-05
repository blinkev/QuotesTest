package com.example.quotes.domain

import io.reactivex.Observable

interface QuoteRepository {
    fun getAll(): List<Quote>
    fun getFavoriteUpdates(): Observable<List<Quote>>
    fun getFavorite(): List<Quote>
    fun addFavorite(quote: Quote)
    fun removeFavorite(quote: Quote)
    fun setFavorite(quotes: List<Quote>)
}