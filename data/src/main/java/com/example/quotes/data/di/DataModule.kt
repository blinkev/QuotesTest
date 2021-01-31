package com.example.quotes.data.di

import android.content.Context
import androidx.room.Room
import com.example.quotes.data.*
import com.example.quotes.data.db.*
import com.example.quotes.data.net.*
import com.example.quotes.domain.QuoteRepository
import com.example.quotes.utils.JsonHelper
import com.example.quotes.utils.JsonHelperImpl
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

    @Singleton
    @Provides
    fun provideDatabase(appContext: Context): Database = Room.databaseBuilder(
        appContext,
        Database::class.java, "app_database"
    ).build()

    @Singleton
    @Provides
    fun provideFavoriteQuoteDao(database: Database): FavoriteQuoteDao = database.favoriteQuoteDao()

    @Singleton
    @Provides
    fun provideFavoriteDtoMapper(mapper: FavoriteQuoteDtoMapperImpl): FavoriteQuoteDtoMapper = mapper

    @Singleton
    @Provides
    fun provideDbHelper(helper: QuoteDbHelperImpl): QuoteDbHelper = helper
}