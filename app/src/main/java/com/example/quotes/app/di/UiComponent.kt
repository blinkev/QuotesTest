package com.example.quotes.app.di

import android.content.Context
import com.example.quotes.app.activity.di.MainActivityComponentProvider
import com.example.quotes.domain.di.DomainProvider
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(dependencies = [DomainProvider::class])
interface UiComponent : MainActivityComponentProvider {

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun appContext(appContext: Context): Builder

        fun domainProvider(provider: DomainProvider): Builder

        fun build(): UiComponent
    }
}