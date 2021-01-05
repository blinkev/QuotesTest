package com.example.quotes.app.activity.di

import androidx.fragment.app.FragmentManager
import com.example.quotes.app.activity.MainActivityRouter
import com.example.quotes.app.activity.routing.AllQuotesRouterImpl
import com.example.quotes.app.activity.routing.FavoriteQuotesRouterImpl
import com.example.quotes.app.activity.routing.MainActivityRouterImpl
import com.example.quotes.app.all.AllQuotesRouter
import com.example.quotes.app.di.scope.ActivityScope
import com.example.quotes.app.favorite.FavoriteQuotesRouter
import dagger.Module
import dagger.Provides

@Module
class MainActivityModule(private val fragmentManager: FragmentManager, private val contentId: Int) {

    @ActivityScope
    @Provides
    fun provideAllQuotesRouter(): AllQuotesRouter = AllQuotesRouterImpl(fragmentManager)

    @ActivityScope
    @Provides
    fun provideFavoriteQuotesRouter(): FavoriteQuotesRouter = FavoriteQuotesRouterImpl(fragmentManager, contentId)

    @ActivityScope
    @Provides
    fun provideMainActivityRouter(): MainActivityRouter = MainActivityRouterImpl(fragmentManager, contentId)
}