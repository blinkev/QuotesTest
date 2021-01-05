package com.example.quotes.app.favorite.di

import com.example.quotes.app.di.scope.FragmentScope
import com.example.quotes.app.favorite.FavoriteQuotesFragment
import com.example.quotes.app.favorite.FavoriteQuotesViewModelImpl
import dagger.Subcomponent

@FragmentScope
@Subcomponent(modules = [FavoriteQuotesModule::class])
interface FavoriteQuotesComponent {
    fun inject(fragment: FavoriteQuotesFragment)
    fun inject(viewModelImpl: FavoriteQuotesViewModelImpl)
}