package com.example.quotes.data.db

import com.example.quotes.domain.Quote
import io.reactivex.Completable
import io.reactivex.Single
import javax.inject.Inject

interface QuoteDbHelper {
    fun getAll(): Single<List<Quote>>
    fun getAllFavorites(): Single<List<Quote>>
    fun addFavoriteQuote(quote: Quote): Completable
    fun deleteFavoriteQuote(quote: Quote): Completable
    fun updateQuotes(quotes: List<Quote>): Completable
    fun setUserOrder(quotes: List<Quote>): Completable
}

class QuoteDbHelperImpl @Inject constructor(
    private val db: Database,
    private val favoriteDao: FavoriteQuoteDao,
    private val mapper: FavoriteQuoteDtoMapper
) : QuoteDbHelper {

    override fun getAll(): Single<List<Quote>> {
        return Single.just(
            listOf(
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
        )
    }

    override fun getAllFavorites(): Single<List<Quote>> = Single.fromCallable {
        favoriteDao.getAll().map(mapper::mapDto)
    }

    override fun addFavoriteQuote(quote: Quote): Completable = completableTransaction {
        val number = favoriteDao.getNextOrderNumber()
        favoriteDao.addQuote(mapper.mapEntity(quote).copy(userOrder = number))
    }

    override fun deleteFavoriteQuote(quote: Quote): Completable = completableTransaction {
        favoriteDao.deleteQuote(mapper.mapEntity(quote))
    }

    override fun setUserOrder(quotes: List<Quote>): Completable = completableTransaction {
        val oldDbQuotes: HashMap<String, FavoriteQuoteDto> = hashMapOf(
            *favoriteDao.getAll()
                .map { it.name to it }
                .toTypedArray()
        )
        val newDbQuotes = quotes.mapIndexed { index, quote ->
            (oldDbQuotes[quote.name] ?: mapper.mapEntity(quote)).copy(userOrder = index)
        }

        favoriteDao.clearTable()
        favoriteDao.addQuotes(newDbQuotes)
    }

    override fun updateQuotes(quotes: List<Quote>): Completable = completableTransaction {
        val newFavorites = favoriteDao.getAll().mapNotNull { oldFavorite ->
            quotes.find { update -> update.name == oldFavorite.name }
                ?.let { update ->
                    oldFavorite.copy(
                        bid = update.bid,
                        ask = update.ask,
                        spread = update.spread
                    )
                }
        }
        favoriteDao.updateQuotes(newFavorites)
    }

    private fun completableTransaction(func: () -> Unit): Completable {
        return Completable.fromCallable {
            db.runInTransaction(func)
        }
    }
}