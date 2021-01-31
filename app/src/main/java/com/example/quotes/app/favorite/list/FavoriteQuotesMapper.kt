package com.example.quotes.app.favorite.list

import com.example.quotes.app.Settings
import com.example.quotes.utils.FormattedString
import com.example.quotes.domain.Quote
import com.example.quotes.utils.ifNotNull
import javax.inject.Inject

interface FavoriteQuotesMapper {
    fun mapInitial(quotes: List<Quote>): List<FavoriteQuoteListItem>
    fun mapUpdates(updates: List<Quote>, old: List<Quote>): List<FavoriteQuoteListItem>
}

class FavoriteQuotesMapperImpl @Inject constructor(
    private val settings: Settings
) : FavoriteQuotesMapper {

    override fun mapInitial(quotes: List<Quote>): List<FavoriteQuoteListItem> {
        val quoteMap: Map<String, Quote> = hashMapOf(*quotes.map { it.name to it }.toTypedArray())

        return settings.favoriteOrder.mapNotNull { favorite ->
            val quoteNullable: Quote? = quoteMap[favorite.name]
            ifNotNull(quoteNullable, formatName(quoteNullable)) { quote, formattedName ->

                FavoriteQuoteListItem(
                    formattedName,
                    formatBidAsk(quote),
                    formatSpread(quote),
                    quote
                )
            }
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

    private fun formatName(quote: Quote?): FormattedString? {
        if (quote == null) return null
        return try {
            val cur1 = quote.name.substring(0..2)
            val cur2 = quote.name.substring(3..5)

            "$cur1 / $cur2"
        } catch (e: Exception) {
            null
        }
    }
}