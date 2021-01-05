package com.example.quotes.app.all

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.quotes.app.BaseFragment
import com.example.quotes.app.HardwareBackClickAware
import com.example.quotes.app.R
import com.example.quotes.app.all.di.AllQuotesComponentProvider
import com.example.quotes.app.all.di.AllQuotesModule
import com.example.quotes.app.all.list.AllQuotesAdapter
import com.example.quotes.ui_kit.DividerDecoration
import kotlinx.android.synthetic.main.fragment_all_quotes.*
import javax.inject.Inject

class AllQuotesFragment : BaseFragment(R.layout.fragment_all_quotes) {

    @Inject
    lateinit var viewModel: AllQuotesViewModel
    @Inject
    lateinit var router: AllQuotesRouter

    private val adapter = AllQuotesAdapter { quote, isChecked ->
        if (isChecked) viewModel.quoteChecked(quote) else viewModel.quoteUnchecked(quote)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        setupComponent()
        super.onCreate(savedInstanceState)
    }

    private fun setupComponent() {
        val viewModel = ViewModelProvider(this).get(AllQuotesViewModelImpl::class.java)
        val component = (activity as AllQuotesComponentProvider).provide(AllQuotesModule(viewModel))
        component.inject(viewModel)
        component.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupToolbar()
        setupList()
        observeAllQuotes()
    }

    private fun setupToolbar() {
        toolbar_view.leftIconClickListener = { onBackPressed() }
    }

    private fun setupList() {
        list_view.setHasFixedSize(true)
        list_view.addItemDecoration(DividerDecoration())
        list_view.adapter = adapter
    }

    private fun observeAllQuotes() {
        viewModel.quotes.observe(viewLifecycleOwner, Observer(adapter::setList))
        if (viewModel.quotes.value == null) viewModel.fetchAllQuotes()
    }

    override fun onBackPressed(): Boolean {
        router.goBack()
        return true
    }
}
