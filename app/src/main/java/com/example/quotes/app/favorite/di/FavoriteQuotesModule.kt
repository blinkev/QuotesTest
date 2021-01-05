package com.example.quotes.app.favorite.di

import com.example.quotes.app.di.scope.FragmentScope
import com.example.quotes.app.favorite.FavoriteQuotesViewModel
import com.example.quotes.app.favorite.list.FavoriteQuotesMapper
import com.example.quotes.app.favorite.list.FavoriteQuotesMapperImpl
import dagger.Module
import dagger.Provides

@Module
class FavoriteQuotesModule(private val viewModel: FavoriteQuotesViewModel) {

    @FragmentScope
    @Provides
    fun provideViewModel(): FavoriteQuotesViewModel = viewModel

    @FragmentScope
    @Provides
    fun provideMapper(mapper: FavoriteQuotesMapperImpl): FavoriteQuotesMapper = mapper
}