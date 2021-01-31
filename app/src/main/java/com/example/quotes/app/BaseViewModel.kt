package com.example.quotes.app

import androidx.lifecycle.ViewModel
import io.reactivex.Completable
import io.reactivex.CompletableOnSubscribe
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable

abstract class BaseViewModel : ViewModel() {

    protected val compositeDisposable = CompositeDisposable()

    override fun onCleared() {
        compositeDisposable.clear()
    }

    protected fun <T> Single<T>.subscribeAndTrack(
        onError: (Throwable) -> Unit = {},
        onSuccess: (T) -> Unit
    ) {
        compositeDisposable.add(this.subscribe(onSuccess, onError))
    }

    protected fun <T> Observable<T>.subscribeAndTrack(
        onComplete: () -> Unit = {},
        onError: (Throwable) -> Unit = {},
        onNext: (T) -> Unit
    ) {
        compositeDisposable.add(this.subscribe(onNext, onError, onComplete))
    }
}