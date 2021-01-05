package com.example.quotes.app.activity

import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.quotes.app.HardwareBackClickAware
import com.example.quotes.app.R
import com.example.quotes.app.activity.di.MainActivityChildComponentProvider
import com.example.quotes.app.activity.di.MainActivityComponent
import com.example.quotes.app.activity.di.MainActivityComponentProvider
import com.example.quotes.app.activity.di.MainActivityModule
import javax.inject.Inject

class MainActivity : AppCompatActivity(), MainActivityChildComponentProvider {

    override lateinit var screenComponent: MainActivityComponent

    @Inject
    lateinit var router: MainActivityRouter

    override fun onCreate(savedInstanceState: Bundle?) {
        setupComponent()
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) router.showFirstScreen()
    }

    private fun setupComponent() {
        screenComponent = (application as MainActivityComponentProvider)
            .provide(MainActivityModule(supportFragmentManager, R.id.content_layout))

        screenComponent.inject(this)
    }

    override fun onBackPressed() {
        var handled = false
        supportFragmentManager.fragments.forEach { fragment ->
            if (fragment.isResumed && fragment is HardwareBackClickAware) {
                if (fragment.onHardwareBackClick()) {
                    handled = true
                    return@forEach
                }
            }
        }

        if (!handled) super.onBackPressed()
    }
}