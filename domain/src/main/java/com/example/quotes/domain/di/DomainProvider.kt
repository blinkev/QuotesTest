package com.example.quotes.domain.di

import com.example.quotes.domain.QuoteRepository

interface DomainProvider {
    val quoteRepository: QuoteRepository
}