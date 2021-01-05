package com.example.quotes.app.activity.di

import com.example.quotes.app.all.di.AllQuotesComponent
import com.example.quotes.app.all.di.AllQuotesComponentProvider
import com.example.quotes.app.all.di.AllQuotesModule
import com.example.quotes.app.favorite.di.FavoriteQuotesComponent
import com.example.quotes.app.favorite.di.FavoriteQuotesComponentProvider
import com.example.quotes.app.favorite.di.FavoriteQuotesModule

interface MainActivityChildComponentProvider : AllQuotesComponentProvider, FavoriteQuotesComponentProvider {

    val screenComponent: MainActivityComponent

    override fun provide(module: AllQuotesModule): AllQuotesComponent =
        screenComponent.provide(module)

    override fun provide(module: FavoriteQuotesModule): FavoriteQuotesComponent =
        screenComponent.provide(module)
}