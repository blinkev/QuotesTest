package com.example.quotes.app.all.di

import com.example.quotes.app.all.AllQuotesFragment
import com.example.quotes.app.all.AllQuotesViewModel
import com.example.quotes.app.all.AllQuotesViewModelImpl
import com.example.quotes.app.di.scope.FragmentScope
import dagger.Subcomponent

@FragmentScope
@Subcomponent(modules = [AllQuotesModule::class])
interface AllQuotesComponent {
    fun inject(fragment: AllQuotesFragment)
    fun inject(viewModelImpl: AllQuotesViewModelImpl)
}