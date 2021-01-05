package com.example.quotes.app.favorite.di

interface FavoriteQuotesComponentProvider {
    fun provide(module: FavoriteQuotesModule): FavoriteQuotesComponent
}