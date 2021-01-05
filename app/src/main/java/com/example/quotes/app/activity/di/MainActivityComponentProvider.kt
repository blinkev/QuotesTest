package com.example.quotes.app.activity.di

interface MainActivityComponentProvider {
    fun provide(module: MainActivityModule): MainActivityComponent
}