package com.example.quotes.data.net

import com.example.quotes.domain.Quote
import com.example.quotes.utils.ifNotNull
import javax.inject.Inject

interface SubscribedListDtoMapper {
    fun map(dto: SubscribedListDto): List<Quote>
}

class SubscribedListDtoMapperImpl @Inject constructor() :
    SubscribedListDtoMapper {

    override fun map(dto: SubscribedListDto): List<Quote> = dto.ticks?.mapNotNull {
        ifNotNull(
            it?.name,
            it?.bid?.toDoubleOrNull(),
            it?.ask?.toDoubleOrNull(),
            it?.spread?.toFloatOrNull()
        ) { name, bid, ask, spread ->
            Quote(
                name = name,
                bid = bid,
                ask = ask,
                spread = spread
            )
        }
    } ?: emptyList()
}