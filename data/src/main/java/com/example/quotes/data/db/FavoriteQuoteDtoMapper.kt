package com.example.quotes.data.db

import com.example.quotes.domain.Quote
import javax.inject.Inject

interface FavoriteQuoteDtoMapper {
    fun mapEntity(entity: Quote): FavoriteQuoteDto
    fun mapDto(dto: FavoriteQuoteDto): Quote
}

class FavoriteQuoteDtoMapperImpl @Inject constructor() : FavoriteQuoteDtoMapper {

    override fun mapEntity(entity: Quote): FavoriteQuoteDto = FavoriteQuoteDto(
        name = entity.name,
        bid = entity.bid,
        ask = entity.ask,
        spread = entity.spread,
        userOrder = -1
    )

    override fun mapDto(dto: FavoriteQuoteDto): Quote = Quote(
        name = dto.name,
        bid = dto.bid,
        ask = dto.ask,
        spread = dto.spread
    )
}