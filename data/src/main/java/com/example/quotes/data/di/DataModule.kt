package com.example.quotes.data.di

import com.example.quotes.data.*
import com.example.quotes.domain.QuoteRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DataModule {

    @Singleton
    @Provides
    fun provideJsonHelper(helper: JsonHelperImpl): JsonHelper = helper

    @Singleton
    @Provides
    fun provideQuoteRepo(repo: QuoteRepositoryImpl): QuoteRepository = repo

    @Singleton
    @Provides
    fun provideQuoteResponseMapper(mapper: SubscribedListDtoMapperImpl): SubscribedListDtoMapper = mapper

    @Singleton
    @Provides
    fun provideWebSocketHelper(helper: WebSocketHelperImpl): WebSocketHelper = helper
}