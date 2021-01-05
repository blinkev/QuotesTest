package com.example.quotes.app.all.di

import com.example.quotes.app.all.AllQuotesViewModel
import com.example.quotes.app.all.list.AllQuotesMapper
import com.example.quotes.app.all.list.AllQuotesMapperImpl
import com.example.quotes.app.di.scope.FragmentScope
import dagger.Module
import dagger.Provides

@Module
class AllQuotesModule(private val viewModel: AllQuotesViewModel) {

    @FragmentScope
    @Provides
    fun provideViewModel(): AllQuotesViewModel = viewModel

    @FragmentScope
    @Provides
    fun provideMapper(mapper: AllQuotesMapperImpl): AllQuotesMapper = mapper
}