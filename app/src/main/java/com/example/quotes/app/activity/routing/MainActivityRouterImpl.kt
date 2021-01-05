package com.example.quotes.app.activity.routing

import androidx.fragment.app.FragmentManager
import com.example.quotes.app.activity.MainActivityRouter
import com.example.quotes.app.favorite.FavoriteQuotesFragment
import javax.inject.Inject

class MainActivityRouterImpl @Inject constructor(
    private val fragmentManager: FragmentManager,
    private val containerId: Int
) : MainActivityRouter {

    override fun showFirstScreen() {
        fragmentManager.beginTransaction()
            .replace(containerId, FavoriteQuotesFragment())
            .commitAllowingStateLoss()
    }
}
