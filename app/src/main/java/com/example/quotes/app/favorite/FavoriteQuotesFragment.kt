package com.example.quotes.app.favorite

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.SimpleItemAnimator
import com.example.quotes.app.BaseFragment
import com.example.quotes.app.R
import com.example.quotes.app.favorite.di.FavoriteQuotesComponentProvider
import com.example.quotes.app.favorite.di.FavoriteQuotesModule
import com.example.quotes.app.favorite.list.FavoriteQuotesAdapter
import com.example.quotes.app.favorite.list.FavoriteQuotesTouchCallback
import com.example.quotes.ui_kit.DividerDecoration
import kotlinx.android.synthetic.main.fragment_favorite_quotes.*
import javax.inject.Inject

class FavoriteQuotesFragment : BaseFragment(R.layout.fragment_favorite_quotes) {

    @Inject
    lateinit var viewModel: FavoriteQuotesViewModel
    @Inject
    lateinit var router: FavoriteQuotesRouter

    private val adapter = FavoriteQuotesAdapter(
        onDeleteClickListener = { viewModel.deleteQuoteFromFavorites(it) },
        reorderListener = { viewModel.onListReordered(it) }
    )
    private val touchHelper = ItemTouchHelper(FavoriteQuotesTouchCallback(adapter))

    override fun onCreate(savedInstanceState: Bundle?) {
        setupComponent()
        super.onCreate(savedInstanceState)
    }

    private fun setupComponent() {
        val viewModel = ViewModelProvider(this).get(FavoriteQuotesViewModelImpl::class.java)
        val component = (activity as FavoriteQuotesComponentProvider).provide(FavoriteQuotesModule(viewModel))
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
        toolbar_view.rightIconClickListener = { router.goToAllQuotes() }
    }

    private fun setupList() {
        list_view.setHasFixedSize(true)
        list_view.adapter = adapter
        (list_view.itemAnimator as? SimpleItemAnimator)?.supportsChangeAnimations = false
        list_view.addItemDecoration(DividerDecoration())
        touchHelper.attachToRecyclerView(list_view)
    }

    private fun observeAllQuotes() {
        viewModel.quotes.observe(viewLifecycleOwner, Observer(adapter::setList))
    }

    override fun onStop() {
        super.onStop()

        viewModel.stopUpdates()
    }

    override fun onStart() {
        super.onStart()

        viewModel.fetchFavoriteQuotes()
    }
}