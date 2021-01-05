package com.example.quotes.app.all

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.quotes.app.BaseViewModel
import com.example.quotes.app.all.list.AllQuotesListItem
import com.example.quotes.app.all.list.AllQuotesMapper
import com.example.quotes.domain.Quote
import com.example.quotes.domain.QuoteRepository
import javax.inject.Inject

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
        val favoriteNames = repo.getFavorite().map { it.name }.toSet()
        quotes.value = repo.getAll().mapNotNull { mapper.map(it, favoriteNames.contains(it.name)) }
    }

    override fun quoteChecked(quote: Quote) {
        repo.addFavorite(quote)
    }

    override fun quoteUnchecked(quote: Quote) {
        repo.removeFavorite(quote)
    }
}
