package com.example.quotes.data.di

import android.content.Context
import com.example.quotes.domain.di.DomainProvider
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [DataModule::class])
interface DataComponent : DomainProvider {

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun appContext(appContext: Context): Builder

        fun build(): DataComponent
    }
}