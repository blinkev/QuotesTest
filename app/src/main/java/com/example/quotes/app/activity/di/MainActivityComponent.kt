package com.example.quotes.app.activity.di

import com.example.quotes.app.activity.MainActivity
import com.example.quotes.app.all.di.AllQuotesComponentProvider
import com.example.quotes.app.di.scope.ActivityScope
import com.example.quotes.app.favorite.di.FavoriteQuotesComponentProvider
import dagger.Subcomponent

@ActivityScope
@Subcomponent(modules = [MainActivityModule::class])
interface MainActivityComponent : AllQuotesComponentProvider, FavoriteQuotesComponentProvider {
    fun inject(mainActivity: MainActivity)
}