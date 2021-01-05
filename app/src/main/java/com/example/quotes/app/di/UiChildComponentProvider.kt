package com.example.quotes.app.di

import com.example.quotes.app.activity.di.MainActivityComponent
import com.example.quotes.app.activity.di.MainActivityComponentProvider
import com.example.quotes.app.activity.di.MainActivityModule

interface UiChildComponentProvider : MainActivityComponentProvider {

    val uiComponent: UiComponent

    override fun provide(module: MainActivityModule): MainActivityComponent =
        uiComponent.provide(module)
}