package com.example.quotes.app

import android.app.Application
import com.example.quotes.app.di.DaggerUiComponent
import com.example.quotes.app.di.UiChildComponentProvider
import com.example.quotes.app.di.UiComponent
import com.example.quotes.data.di.DaggerDataComponent

class App : Application(), UiChildComponentProvider {

    override val uiComponent: UiComponent by lazy {
        val dataComponent = DaggerDataComponent.builder()
            .appContext(applicationContext)
            .build()

        DaggerUiComponent.builder()
                .domainProvider(dataComponent)
                .appContext(applicationContext)
                .build()
    }
}