package com.example.quotes.app

import androidx.fragment.app.Fragment

abstract class BaseFragment(layoutId: Int) : Fragment(layoutId), HardwareBackClickAware {

    /**
     * Вспомогательный метод проверяющий не обработал ли кто-нибудь из child фрагментов хардварный бек
     */
    final override fun onHardwareBackClick(): Boolean {
        for (fragment in childFragmentManager.fragments) {
            if (fragment.isResumed && fragment is HardwareBackClickAware) {
                if (fragment.onHardwareBackClick()) return true
            }
        }

        return onBackPressed()
    }

    /**
     * Используется чтобы настроить поведение при клике на хардварный бек (сработает если никто из
     * child фрагментов этого не сделал)
     */
    protected open fun onBackPressed() = false
}