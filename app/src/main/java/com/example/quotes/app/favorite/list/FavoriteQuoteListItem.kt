package com.example.quotes.app.favorite.list

import com.example.quotes.utils.FormattedString
import com.example.quotes.domain.Quote

data class FavoriteQuoteListItem(
    val name: FormattedString,
    val bidAsk: FormattedString,
    val spread: FormattedString,
    val quote: Quote
)