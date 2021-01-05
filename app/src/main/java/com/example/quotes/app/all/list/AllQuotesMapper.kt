package com.example.quotes.app.all.list

import com.example.quotes.domain.Quote
import javax.inject.Inject

interface AllQuotesMapper {
    fun map(quote: Quote, isChecked: Boolean): AllQuotesListItem?
}

class AllQuotesMapperImpl @Inject constructor() : AllQuotesMapper {

    override fun map(quote: Quote, isChecked: Boolean): AllQuotesListItem? {
        return try {
            val cur1 = quote.name.substring(0..2)
            val cur2 = quote.name.substring(3..5)

            AllQuotesListItem("$cur1 / $cur2", isChecked, quote)
        } catch (e: Exception) {
            null
        }
    }
}