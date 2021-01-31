package com.example.quotes.domain

import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single

interface QuoteRepository {
    fun getAll(): Single<List<Quote>>
    fun getFavoriteUpdates(): Observable<List<Quote>>
    fun getFavorites(): Single<List<Quote>>
    fun addFavorite(quote: Quote): Completable
    fun removeFavorite(quote: Quote): Completable
    fun setOrder(quotes: List<Quote>): Completable
}