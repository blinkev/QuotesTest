package com.example.quotes.app.favorite

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.quotes.app.BaseViewModel
import com.example.quotes.app.favorite.list.FavoriteQuoteListItem
import com.example.quotes.app.favorite.list.FavoriteQuotesMapper
import com.example.quotes.domain.Quote
import com.example.quotes.domain.QuoteRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

interface FavoriteQuotesViewModel {
    val quotes: LiveData<List<FavoriteQuoteListItem>>

    fun fetchFavoriteQuotes()
    fun stopUpdates()
    fun onListReordered(list: List<Quote>)
    fun deleteQuoteFromFavorites(quote: Quote)
}

class FavoriteQuotesViewModelImpl : BaseViewModel(), FavoriteQuotesViewModel {

    @Inject
    lateinit var repo: QuoteRepository
    @Inject
    lateinit var mapper: FavoriteQuotesMapper

    override val quotes = MutableLiveData<List<FavoriteQuoteListItem>>()

    override fun deleteQuoteFromFavorites(quote: Quote) {
        quotes.value = quotes.value?.filterNot { it.quote.name == quote.name }
        repo.removeFavorite(quote)
    }

    override fun fetchFavoriteQuotes() {
        repo.getFavorite().mapNotNull { mapper.mapInitial(it) }.let {
            quotes.value = it
            if (it.isNotEmpty()) startUpdates()
        }
    }

    override fun stopUpdates() {
        compositeDisposable.clear()
    }

    override fun onListReordered(list: List<Quote>) {
        repo.setFavorite(list)
    }

    private fun startUpdates() {
        repo.getFavoriteUpdates()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .map {  updates ->
                val old = quotes.value?.map { item -> item.quote } ?: emptyList()
                mapper.mapUpdates(updates, old)
            }
            .subscribeAndTrack(onNext = { quotes.value = it })
    }
}
