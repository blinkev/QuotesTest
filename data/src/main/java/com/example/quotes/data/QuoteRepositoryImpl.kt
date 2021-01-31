package com.example.quotes.data

import com.example.quotes.data.db.QuoteDbHelper
import com.example.quotes.data.net.WebSocketHelper
import com.example.quotes.domain.Quote
import com.example.quotes.domain.QuoteRepository
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import javax.inject.Inject

class QuoteRepositoryImpl @Inject constructor(
    private val webSocketHelper: WebSocketHelper,
    private val dbHelper: QuoteDbHelper
) : QuoteRepository {

    override fun getAll(): Single<List<Quote>> = dbHelper.getAll()

    override fun setOrder(quotes: List<Quote>): Completable = dbHelper.setUserOrder(quotes)

    override fun getFavoriteUpdates(): Observable<List<Quote>> {
        return getFavorites().flatMapObservable { favorites ->
            webSocketHelper.subscribe(favorites)
                .flatMap { updates ->
                    dbHelper.updateQuotes(updates)
                        .onErrorComplete()
                        .andThen(Observable.just(updates))
                }
        }
    }

    override fun addFavorite(quote: Quote): Completable = dbHelper.addFavoriteQuote(quote)

    override fun removeFavorite(quote: Quote): Completable {
        webSocketHelper.unsubscribe(quote)
        return dbHelper.deleteFavoriteQuote(quote)
    }

    override fun getFavorites(): Single<List<Quote>> = dbHelper.getAllFavorites()
}
