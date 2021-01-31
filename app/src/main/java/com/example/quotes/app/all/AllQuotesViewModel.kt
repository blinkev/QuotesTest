package com.example.quotes.app.all

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.quotes.app.BaseViewModel
import com.example.quotes.app.all.list.AllQuotesListItem
import com.example.quotes.app.all.list.AllQuotesMapper
import com.example.quotes.domain.Quote
import com.example.quotes.domain.QuoteRepository
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.BiFunction
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject
import javax.inject.Singleton

interface AllQuotesViewModel {
    val quotes: LiveData<List<AllQuotesListItem>>

    fun fetchAllQuotes()
    fun quoteChecked(quote: Quote)
    fun quoteUnchecked(quote: Quote)
}

class AllQuotesViewModelImpl : BaseViewModel(), AllQuotesViewModel {

    @Inject
    lateinit var repo: QuoteRepository

    @Inject
    lateinit var mapper: AllQuotesMapper

    override val quotes = MutableLiveData<List<AllQuotesListItem>>()

    override fun fetchAllQuotes() {
        Single.zip<List<Quote>, List<Quote>, List<AllQuotesListItem>>(
            repo.getAll(),
            repo.getFavorites(),
            BiFunction { all, favorites ->
                val favoriteNames = favorites.map { it.name }.toHashSet()
                all.mapNotNull { mapper.map(it, favoriteNames.contains(it.name)) }
            })
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeAndTrack(
                onSuccess = {
                    quotes.value = it
                }
            )
    }

    override fun quoteChecked(quote: Quote) {
        repo.addFavorite(quote).subscribe()
    }

    override fun quoteUnchecked(quote: Quote) {
        repo.removeFavorite(quote).subscribe()
    }
}
