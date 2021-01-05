package com.example.quotes.app.favorite.list

import com.example.quotes.utils.FormattedString
import com.example.quotes.domain.Quote
import javax.inject.Inject

interface FavoriteQuotesMapper {
    fun mapInitial(quote: Quote): FavoriteQuoteListItem?
    fun mapUpdates(updates: List<Quote>, old: List<Quote>): List<FavoriteQuoteListItem>
}

class FavoriteQuotesMapperImpl @Inject constructor() : FavoriteQuotesMapper {

    override fun mapInitial(quote: Quote): FavoriteQuoteListItem? {
        return formatName(quote)?.let { formattedName ->

            FavoriteQuoteListItem(
                formattedName,
                formatBidAsk(quote),
                formatSpread(quote),
                quote
            )
        }
    }

    override fun mapUpdates(updates: List<Quote>, old: List<Quote>): List<FavoriteQuoteListItem> {
        return old.mapNotNull { oldQuote ->

            val updatedQuote = updates.find { it.name == oldQuote.name } ?: oldQuote

            formatName(oldQuote)?.let { formattedName ->
                FavoriteQuoteListItem(
                    formattedName,
                    formatBidAsk(updatedQuote),
                    formatSpread(updatedQuote),
                    updatedQuote
                )
            }
        }
    }

    private fun formatBidAsk(quote: Quote): FormattedString {
        val bid: String = if (quote.bid == -1.0) "-" else quote.bid.toString()
        val ask: String = if (quote.ask == -1.0) "-" else quote.ask.toString()

        return "$bid / $ask"
    }

    private fun formatSpread(quote: Quote): FormattedString {
        return if (quote.spread == -1f) "-" else quote.spread.toString()
    }

    private fun formatName(quote: Quote): FormattedString? {
        return try {
            val cur1 = quote.name.substring(0..2)
            val cur2 = quote.name.substring(3..5)

            "$cur1 / $cur2"
        } catch (e: Exception) {
            null
        }
    }
}