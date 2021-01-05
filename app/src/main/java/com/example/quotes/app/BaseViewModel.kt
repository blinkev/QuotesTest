package com.example.quotes.app

import androidx.lifecycle.ViewModel
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable

abstract class BaseViewModel : ViewModel() {

    protected val compositeDisposable = CompositeDisposable()

    override fun onCleared() {
        compositeDisposable.clear()
    }

    protected fun <T> Observable<T>.subscribeAndTrack(
        onComplete: () -> Unit = {},
        onError: (Throwable) -> Unit = {},
        onNext: (T) -> Unit
    ) {
        compositeDisposable.add(this.subscribe(onNext, onError, onComplete))
    }
}