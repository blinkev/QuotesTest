package com.example.quotes.domain

data class Quote(
    val name: String,
    val bid: Double,
    val ask: Double,
    val spread: Float
)