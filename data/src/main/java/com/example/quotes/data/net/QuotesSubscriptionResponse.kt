package com.example.quotes.data.net

import com.google.gson.annotations.SerializedName

data class QuotesSubscriptionResponse(
    @SerializedName("subscribed_count")
    val subscribedCount: Int?,

    @SerializedName("subscribed_list")
    val subscribedList: SubscribedListDto?
)

data class SubscribedListDto(val ticks: List<QuoteTickDto?>?)

data class QuoteTickDto(
    @SerializedName("s")
    val name: String?,

    @SerializedName("b")
    val bid: String?,

    @SerializedName("a")
    val ask: String?,

    @SerializedName("spr")
    val spread: String?
)