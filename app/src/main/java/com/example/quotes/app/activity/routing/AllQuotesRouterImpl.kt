package com.example.quotes.app.activity.routing

import androidx.fragment.app.FragmentManager
import com.example.quotes.app.all.AllQuotesRouter
import javax.inject.Inject

class AllQuotesRouterImpl @Inject constructor(private val fragmentManager: FragmentManager) : AllQuotesRouter {

    override fun goBack() {
        fragmentManager.popBackStack()
    }
}