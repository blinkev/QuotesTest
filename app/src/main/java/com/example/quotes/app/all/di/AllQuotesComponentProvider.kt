package com.example.quotes.app.all.di

interface AllQuotesComponentProvider {
    fun provide(module: AllQuotesModule): AllQuotesComponent
}