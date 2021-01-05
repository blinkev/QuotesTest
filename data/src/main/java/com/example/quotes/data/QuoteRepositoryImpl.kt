package com.example.quotes.data

import android.content.Context
import android.content.SharedPreferences
import com.example.quotes.domain.Quote
import com.example.quotes.domain.QuoteRepository
import io.reactivex.Observable
import io.reactivex.Single
import javax.inject.Inject

class QuoteRepositoryImpl @Inject constructor(
    private val appContext: Context,
    private val jsonHelper: JsonHelper,
    private val webSocketHelper: WebSocketHelper
) : QuoteRepository {

    companion object {
        private const val PREF_NAME = "QUOTE_REPO_PREF_NAME"
        private const val FAVORITES_KEY = "FAVORITES_KEY"
    }

    private val pref: SharedPreferences by lazy {
        appContext.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
    }

    override fun getAll(): List<Quote> = listOf(
        Quote("BTCUSD", -1.0, -1.0, -1f),
        Quote("EURUSD", -1.0, -1.0, -1f),
        Quote("EURGBP", -1.0, -1.0, -1f),
        Quote("USDJPY", -1.0, -1.0, -1f),
        Quote("GBPUSD", -1.0, -1.0, -1f),
        Quote("USDCHF", -1.0, -1.0, -1f),
        Quote("USDCAD", -1.0, -1.0, -1f),
        Quote("AUDUSD", -1.0, -1.0, -1f),
        Quote("EURJPY", -1.0, -1.0, -1f),
        Quote("EURCHF", -1.0, -1.0, -1f)
    )

    override fun getFavoriteUpdates(): Observable<List<Quote>> {
        return webSocketHelper.subscribe(getFavorite())
            .doOnNext { updates ->
                val newFavorites = getFavorite().map { favorite ->
                    updates.find { update -> update.name == favorite.name }?.let { update ->
                        favorite.copy(bid = update.bid, ask = update.ask, spread = update.spread)
                    } ?: favorite
                }
                setFavorite(newFavorites)
            }
    }

    override fun addFavorite(quote: Quote) {
        setFavorite(getFavorite().toMutableList().apply { add(quote) })
    }

    override fun removeFavorite(quote: Quote) {
        setFavorite(getFavorite().filterNot { it.name == quote.name })
        webSocketHelper.unsubscribe(quote)
    }

    override fun setFavorite(quotes: List<Quote>) {
        pref.edit()
            .putString(FAVORITES_KEY, jsonHelper.toJson(quotes))
            .apply()
    }

    override fun getFavorite(): List<Quote> {
        return pref.getString(FAVORITES_KEY, "[]")
            .let { jsonHelper.fromJson(it, Array<Quote>::class.java)?.toList() ?: emptyList() }
    }
}
