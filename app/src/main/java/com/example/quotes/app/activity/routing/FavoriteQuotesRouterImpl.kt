package com.example.quotes.app.activity.routing

import androidx.fragment.app.FragmentManager
import com.example.quotes.app.all.AllQuotesFragment
import com.example.quotes.app.all.AllQuotesRouter
import com.example.quotes.app.favorite.FavoriteQuotesRouter
import javax.inject.Inject

class FavoriteQuotesRouterImpl @Inject constructor(
    private val fragmentManager: FragmentManager,
    private val containerId: Int
) : FavoriteQuotesRouter {

    override fun goToAllQuotes() {
        fragmentManager.beginTransaction()
            .replace(containerId, AllQuotesFragment())
            .addToBackStack(AllQuotesFragment::class.java.name)
            .commitAllowingStateLoss()
    }
}